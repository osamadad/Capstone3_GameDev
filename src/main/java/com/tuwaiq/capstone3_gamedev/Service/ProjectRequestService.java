package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOIn.ProjectRequestDTO;
import com.tuwaiq.capstone3_gamedev.Model.*;
import com.tuwaiq.capstone3_gamedev.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectRequestService {

    private final ProjectRequestRepository projectRequestRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectPositionRepository projectPositionRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final StudioMemberRepository studioMemberRepository;


    public void addProjectRequest(Integer leaderId,ProjectRequestDTO projectRequestDTO){
        StudioMember studioMember=studioMemberRepository.findStudioMemberById(leaderId);
        if (studioMember==null){
            throw new ApiException("Studio member not found");
        }
        StudioMember leaderStudioMember=studioMemberRepository.getLeaderOfStudioByStudioId(studioMember.getStudio().getId());
        if (!studioMember.getId().equals(leaderStudioMember.getId())){
            throw new ApiException("You are not the leader of this studio");
        }
        User user = userRepository.findUserById(projectRequestDTO.getUserId());
        if (user==null){
            throw new ApiException("User not found");
        }
        if (user.getStudioMember()!=null){
            throw new ApiException("The user is already in a studio you can't make another request");
        }
        Project project=projectRepository.findProjectById(projectRequestDTO.getProjectId());
        if (project==null){
            throw new ApiException("Project not found");
        }
        if (!project.getStudio().getId().equals(leaderStudioMember.getStudio().getId())){
            throw new ApiException("You can't create a request for a project that is not yours");
        }
        ProjectPosition projectPosition=projectPositionRepository.findProjectPositionById(projectRequestDTO.getProjectPositionId());
        if (projectPosition==null){
            throw new ApiException("Project position not found");
        }
        ProjectRequest projectRequest=new ProjectRequest(null,projectRequestDTO.getMessage(),"Pending", LocalDateTime.now(),user,project,projectPosition);
        projectRequestRepository.save(projectRequest);

        StudioMember leader = project.getStudio().getMembers().stream()
                .filter(m -> "leader".equalsIgnoreCase(m.getRole()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Leader not found"));

        sendRequestUserEmail(user.getEmail(), user.getUsername(), leader.getUser().getEmail(),project.getName());
    }

    public List<ProjectRequest> getProjectRequests(){
        return projectRequestRepository.findAll();
    }

    public void deleteProjectRequest(Integer leaderId, Integer projectRequestId){
        ProjectRequest oldRequest = projectRequestRepository.findProjectRequestById(projectRequestId);
        if (oldRequest == null) {
            throw new ApiException("Request not found");
        }
        StudioMember studioMember=studioMemberRepository.findStudioMemberById(leaderId);
        if (studioMember==null){
            throw new ApiException("Studio member not found");
        }
        StudioMember leaderStudioMember=studioMemberRepository.getLeaderOfStudioByStudioId(studioMember.getStudio().getId());
        if (!studioMember.getId().equals(leaderStudioMember.getId())){
            throw new ApiException("You are not the leader of this studio");
        }
        if (!oldRequest.getProject().getStudio().getId().equals(leaderStudioMember.getStudio().getId())){
            throw new ApiException("You can't delete a request for a project that is not yours");
        }
        projectRequestRepository.delete(oldRequest);
    }

    //system endpoint
    public void acceptProjectRequest(Integer userId, Integer projectRequestId) {
        ProjectRequest projectRequest = projectRequestRepository.findProjectRequestById(projectRequestId);
        if (projectRequest == null) {
            throw new ApiException("Project request not found");
        }
        if (projectRequest.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("Cannot accept a rejected request");
        }
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("user not found");
        }
        if (!user.getId().equals(projectRequest.getUser().getId())){
            throw new ApiException("You are not the user of this request, you can't accept request that are not yours");
        }
        ProjectMember projectMember=new ProjectMember();
        String compensationType=projectRequest.getProjectPosition().getCompensationType();
        Double compensation=projectRequest.getProjectPosition().getCompensation();
        projectMember.setCompensationType(compensationType);
        if (compensationType.equalsIgnoreCase("paid salary")){
            projectMember.setCompensationAmount(compensation);
        }else if (compensationType.equalsIgnoreCase("revenue share")){
            projectMember.setRevenueSharePercentage(compensation);
        }else {
            projectMember.setCompensationAmount(0.0);
        }
        projectMember.setHoursPerWeek(projectRequest.getProjectPosition().getHoursPerWeek());
        projectMember.setCreatedAt(LocalDateTime.now());
        projectMember.setUser(projectRequest.getUser());
        projectMember.setProject(projectRequest.getProject());

        StudioMember studioMember = new StudioMember();
        studioMember.setRole("member");
        studioMember.setCreatedAt(LocalDateTime.now());
        studioMember.setUser(projectRequest.getUser());
        studioMember.setStudio(projectRequest.getProject().getStudio());

        projectMemberRepository.save(projectMember);
        studioMemberRepository.save(studioMember);
        projectRequest.setStatus("Accepted");
        projectRequestRepository.save(projectRequest);

        StudioMember leaderStudioMember=studioMemberRepository.getLeaderOfStudioByStudioId(studioMember.getStudio().getId());


        projectRequestAccepted(leaderStudioMember.getUser().getEmail(),user.getUsername(),projectRequest.getProject().getName());

    }

    //system endpoint
    public void rejectRequest(Integer userId,Integer projectId) {
        ProjectRequest projectRequest = projectRequestRepository.findProjectRequestById(projectId);
        if (projectRequest == null) {
            throw new ApiException("Request not found");
        }
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("leader not found");
        }
        if (!user.getId().equals(projectRequest.getUser().getId())){
            throw new ApiException("You are not the user of this request, you can't reject request that are not yours");
        }
        if (projectRequest.getStatus().equalsIgnoreCase("Accepted")) {
            throw new ApiException("Cannot reject an accepted request");
        }
        projectRequest.setStatus("Rejected");
        projectRequestRepository.save(projectRequest);

        StudioMember leader = projectRequest.getProject().getStudio().getMembers().stream()
                .filter(m -> "leader".equalsIgnoreCase(m.getRole()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Leader not found"));

        projectRequestRejected(leader.getUser().getEmail(),user.getUsername(),projectRequest.getProject().getName());

    }
    private void sendRequestUserEmail(String userEmail, String username,String leaderEmail,String projectName) {
        String webhookUrl = "http://localhost:5678/webhook/send-request-user-email";

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("email", userEmail);
        payload.put("username", username);
        payload.put("leaderEmail", leaderEmail);
        payload.put("projectName", projectName);

        new RestTemplate().postForObject(
                webhookUrl,
                payload,
                String.class
        );
    }

    private void projectRequestAccepted(String leaderEmail, String username,String projectName) {
        String webhookUrl = "http://localhost:5678/webhook/send-accepted-project-request-email";

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("leaderEmail", leaderEmail);
        payload.put("username", username);
        payload.put("projectName", projectName);

        new RestTemplate().postForObject(
                webhookUrl,
                payload,
                String.class
        );
    }

    private void projectRequestRejected(String leaderEmail, String username,String projectName) {
        String webhookUrl = "http://localhost:5678/webhook/send-rejected-project-request-email";

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("leaderEmail", leaderEmail);
        payload.put("username", username);
        payload.put("projectName", projectName);

        new RestTemplate().postForObject(
                webhookUrl,
                payload,
                String.class
        );
    }

}
