package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOIn.UserRequestDTO;
import com.tuwaiq.capstone3_gamedev.Model.*;
import com.tuwaiq.capstone3_gamedev.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserRequestService {
    private final UserRequestRepository userRequestRepository;
    private final UserRepository userRepository;
    private final StudioRepository studioRepository;
    private final ProjectRepository projectRepository;
    private final ProjectPositionRepository projectPositionRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final StudioMemberRepository studioMemberRepository;

    public List<UserRequest> getUserRequests() {
        return userRequestRepository.findAll();
    }

    public void addUserRequest(UserRequestDTO requestDTO) {
        User user = userRepository.findUserById(requestDTO.getUserId());
        if (user==null){
            throw new ApiException("User not found");
        }
        if (user.getStudioMember()!=null){
            throw new ApiException("You are already in a studio you can't make another request");
        }
        Project project=projectRepository.findProjectById(requestDTO.getProjectId());
        if (project==null){
            throw new ApiException("Project not found");
        }
        ProjectPosition projectPosition=projectPositionRepository.findProjectPositionById(requestDTO.getProjectPositionId());
        if (projectPosition==null){
            throw new ApiException("Project position not found");
        }
        UserRequest userRequest=new UserRequest(null,requestDTO.getMessage(),"Pending",LocalDateTime.now(),user,project,projectPosition);
        userRequestRepository.save(userRequest);
    }


    public void updateUserRequest(Integer userId, Integer UserRequestId, UserRequestDTO UserRequestDTO) {
        UserRequest old = userRequestRepository.findUserRequestById(UserRequestId);
        if (old == null) {
            throw new ApiException("User request not found");
        }
        if (!userId.equals(old.getUser().getId())){
            throw new ApiException("You can't update a request that is not yours");
        }
        old.setMessage(UserRequestDTO.getMessage());
        userRequestRepository.save(old);
    }


    public void deleteUserRequest(Integer userId,Integer UserRequestId) {
        UserRequest old = userRequestRepository.findUserRequestById(UserRequestId);
        if (old == null) {
            throw new ApiException("Request not found");
        }
        if (!userId.equals(old.getUser().getId())){
            throw new ApiException("You can't update a request that is not yours");
        }
        userRequestRepository.delete(old);
    }

   //Endpoints
    public void acceptRequest(Integer leaderId, Integer UserRequestId) {
        UserRequest req = userRequestRepository.findUserRequestById(UserRequestId);
        if (req == null) {
            throw new ApiException("Request not found");
        }
        if (req.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("Cannot accept a rejected request");
        }
        checkStudioLeader(leaderId,req.getProject());
        ProjectMember projectMember=new ProjectMember();
        String compensationType=req.getProjectPosition().getCompensationType();
        Double compensation=req.getProjectPosition().getCompensation();
        projectMember.setCompensationType(compensationType);
        if (compensationType.equalsIgnoreCase("paid salary")){
            projectMember.setCompensationAmount(compensation);
        }else if (compensationType.equalsIgnoreCase("revenue share")){
            projectMember.setRevenueSharePercentage(compensation);
        }else {
            projectMember.setCompensationAmount(0.0);
        }
        projectMember.setHoursPerWeek(req.getProjectPosition().getHoursPerWeek());
        projectMember.setCreated_at(LocalDateTime.now());
        projectMember.setUser(req.getUser());
        projectMember.setProject(req.getProject());

        StudioMember studioMember = new StudioMember();
        studioMember.setRole("member");
        studioMember.setCreatedAt(LocalDateTime.now());
        studioMember.setUser(req.getUser());

        projectMemberRepository.save(projectMember);
        studioMemberRepository.save(studioMember);
        req.setStatus("Accepted");
        userRequestRepository.save(req);
    }


    public void rejectRequest(Integer leaderId,Integer id) {
        UserRequest req = userRequestRepository.findUserRequestById(id);
        if (req == null) {
            throw new ApiException("Request not found");
        }
        checkStudioLeader(leaderId,req.getProject());
        if (req.getStatus().equalsIgnoreCase("Accepted")) {
            throw new ApiException("Cannot reject an accepted request");
        }
        req.setStatus("Rejected");
        userRequestRepository.save(req);
    }

    private void checkStudioLeader(Integer leaderId,Project project) {
        StudioMember studioMember=studioMemberRepository.findStudioMemberById(leaderId);
        if (studioMember==null){
            throw new ApiException("Studio member not found");
        }
        StudioMember leaderStudioMember=studioMemberRepository.getLeaderOfStudioByStudioId(studioMember.getStudio().getId());
        if (!studioMember.getId().equals(leaderStudioMember.getId())){
            throw new ApiException("You are not the leader of this studio");
        }
        if (!project.getStudio().getId().equals(leaderStudioMember.getStudio().getId())){
            throw new ApiException("This project does not belong to your studio");
        }
    }

    public List<UserRequest> getUserRequestsByUserId(Integer userId) {
        UserRequest user = userRequestRepository.findUserRequestById(userId);
        if (user==null) {
            throw new ApiException("User not found");
        }

        List<UserRequest> requests = userRequestRepository.findAllByUserId(userId);

        if (requests.isEmpty()) {
            throw new ApiException("No requests found for user with id: " + userId);
        }

        return requests;
    }

//    public List<UserRequest> getRequestsByStudioId(Integer studioId) {
//        if (!studioRepository.existsById(studioId)) {
//            throw new ApiException("Studio not found");
//        }
//
//        List<UserRequest> requests = userRequestRepository.findAllByStudioId(studioId);
//
//        if (requests.isEmpty()) {
//            throw new ApiException("No requests found for studio with id: "+ studioId);
//        }
//
//        return requests;
//    }


}
