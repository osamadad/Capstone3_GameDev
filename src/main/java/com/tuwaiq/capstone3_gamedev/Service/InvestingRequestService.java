package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOIn.InvestingRequestDTO;
import com.tuwaiq.capstone3_gamedev.Model.*;
import com.tuwaiq.capstone3_gamedev.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class InvestingRequestService {

    private final InvestingRequestRepository investingRequestRepository;
    private final InvestorRepository investorRepository;
    private final ProjectRepository projectRepository;
    private final ProjectInvestorRepository projectInvestorRepository;
    private final StudioMemberRepository studioMemberRepository;

    public List<InvestingRequest> getInvestingRequests() {
        return investingRequestRepository.findAll();
    }


    public void addInvestingRequest(InvestingRequestDTO investingRequestDTO) {
        Investor investor = investorRepository.findInvestorById(investingRequestDTO.getInvestorId());
        if (investor==null){
            throw new ApiException("Investor not found");
        }
        if (!investor.getStatus().equalsIgnoreCase("Accepted")){
            throw new ApiException("You are not an accepted investor, wait for admin to approve of you");
        }
        Project project = projectRepository.findProjectById(investingRequestDTO.getProjectId());
        if (project==null) {
            throw new ApiException("Project not found");
        }
        boolean alreadyRequested = investingRequestRepository.existsByInvestorIdAndProjectId(investor.getId(), project.getId());
        if (alreadyRequested) {
            throw new ApiException("Investor already has a request for this project");
        }
        InvestingRequest investingRequest= new InvestingRequest(null,investingRequestDTO.getInvestingOffer(), investingRequestDTO.getEquityShare(), "Pending",LocalDateTime.now(),project,investor);
        investingRequestRepository.save(investingRequest);

        StudioMember leader = project.getStudio().getMembers().stream()
                .filter(m -> "leader".equalsIgnoreCase(m.getRole()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Leader not found"));

        sendInvestorRequestProjectEmail(investor.getEmail(),investor.getFullName(),leader.getUser().getEmail(),project.getName());

    }


    public void updateInvestingRequest(Integer investorId, Integer id, InvestingRequestDTO investingRequestDTO) {
        InvestingRequest old = investingRequestRepository.findInvestingRequestById(id);
        if(old==null){
            throw new ApiException("Request not found");
        }
        if (!investorId.equals(old.getInvestor().getId())){
            throw new ApiException("You are not the investor for this request");
        }
        old.setInvestingOffer(investingRequestDTO.getInvestingOffer());
        old.setEquityShare(investingRequestDTO.getEquityShare());
        investingRequestRepository.save(old);
    }


    public void deleteInvestingRequest(Integer investorId, Integer id) {
        InvestingRequest investingRequest = investingRequestRepository.findInvestingRequestById(id);
        if(investingRequest ==null){
            throw new ApiException("Request not found");
        }
        if (!investorId.equals(investingRequest.getInvestor().getId())){
            throw new ApiException("You are not the investor for this request");
        }
        investingRequestRepository.delete(investingRequest);
    }


    //Endpoints
    public void acceptRequest(Integer leaderId, Integer id) {
        InvestingRequest req = investingRequestRepository.findInvestingRequestById(id);
        if(req==null){
            throw new ApiException("Request not found");
        }
        if (req.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("Cannot accept a rejected request");
        }
        Project project=req.getProject();
        if (project==null){
            throw new ApiException("Project not found");
        }
        checkStudioLeader(leaderId, project);
        Investor investor=req.getInvestor();
        if (investor==null){
            throw new ApiException("Investor not found");
        }
        ProjectInvestor projectInvestor=new ProjectInvestor(null, req.getInvestingOffer(), req.getEquityShare(), LocalDateTime.now(),project,investor);
        projectInvestorRepository.save(projectInvestor);
        req.setStatus("Accepted");
        investingRequestRepository.save(req);

        investorRequestAccepted(req.getInvestor().getEmail(),req.getInvestor().getFullName(),req.getProject().getName());

    }


    public void rejectRequest(Integer leaderId, Integer id) {
        InvestingRequest req = investingRequestRepository.findInvestingRequestById(id);
        if(req==null){
            throw new ApiException("Request not found");
        }
        if (req.getStatus().equalsIgnoreCase("Accepted")) {
            throw new ApiException("Cannot reject an accepted request");
        }
        checkStudioLeader(leaderId, req.getProject());
        req.setStatus("Rejected");
        investingRequestRepository.save(req);

        investorRequestRejected(req.getInvestor().getEmail(),req.getInvestor().getFullName(),req.getProject().getName());

    }

    private void checkStudioLeader(Integer leaderId, Project project) {
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

    public List<InvestingRequest> getRequestsByInvestorId(Integer investorId) {
        if (!investorRepository.existsById(investorId)) {
            throw new ApiException("Investor not found");
        }

        List<InvestingRequest> requests = investingRequestRepository.findAllByInvestorId(investorId);

        if (requests.isEmpty()) {
            throw new ApiException("No investing requests found for investor with id: "+ investorId);
        }

        return requests;
    }

    public List<InvestingRequest> getRequestsByProjectId(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ApiException("Project not found");
        }

        List<InvestingRequest> requests = investingRequestRepository.findAllByProjectId(projectId);

        if (requests.isEmpty()) {
            throw new ApiException("No investing requests found for project with id: "+projectId);
        }

        return requests;
    }

    private void sendInvestorRequestProjectEmail(String investorEmail, String fullName,String leaderEmail,String projectName) {
        String webhookUrl = "http://localhost:5678/webhook/send-Investor-request-project-email";

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("investorEmail", investorEmail);
        payload.put("fullName", fullName);
        payload.put("leaderEmail", leaderEmail);
        payload.put("projectName", projectName);

        new RestTemplate().postForObject(
                webhookUrl,
                payload,
                String.class
        );
    }

    private void investorRequestAccepted(String investorEmail, String fullName,String projectName) {
        String webhookUrl = "http://localhost:5678/webhook/send-accepted-request-email";

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("investorEmail", investorEmail);
        payload.put("fullName", fullName);
        payload.put("projectName", projectName);

        new RestTemplate().postForObject(
                webhookUrl,
                payload,
                String.class
        );
    }

    private void investorRequestRejected(String investorEmail, String fullName,String projectName) {
        String webhookUrl = "http://localhost:5678/webhook/send-rejected-request-email";

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("investorEmail", investorEmail);
        payload.put("fullName", fullName);
        payload.put("projectName", projectName);

        new RestTemplate().postForObject(
                webhookUrl,
                payload,
                String.class
        );
    }



}
