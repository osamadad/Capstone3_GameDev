package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOIn.InvestingRequestDTO;
import com.tuwaiq.capstone3_gamedev.Model.InvestingRequest;
import com.tuwaiq.capstone3_gamedev.Model.Investor;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.ProjectInvestor;
import com.tuwaiq.capstone3_gamedev.Repository.InvestingRequestRepository;
import com.tuwaiq.capstone3_gamedev.Repository.InvestorRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectInvestorRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class InvestingRequestService {
    private final InvestingRequestRepository investingRequestRepository;
    private final InvestorRepository investorRepository;
    private final ProjectRepository projectRepository;
    private final ProjectInvestorRepository projectInvestorRepository;

    public List<InvestingRequest> getAll() {
        return investingRequestRepository.findAll();
    }


    public void add(InvestingRequestDTO investingRequestDTO) {
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
    }


    public void update(Integer investorId, Integer id, InvestingRequestDTO investingRequestDTO) {
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


    public void delete(Integer investorId, Integer id) {
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
        Investor investor=req.getInvestor();
        if (investor==null){
            throw new ApiException("Investor not found");
        }
        ProjectInvestor projectInvestor=new ProjectInvestor(null, req.getInvestingOffer(), req.getEquityShare(), LocalDateTime.now(),project,investor);
        projectInvestorRepository.save(projectInvestor);
        req.setStatus("Accepted");
        investingRequestRepository.save(req);
    }


    public void rejectRequest(Integer id) {
        InvestingRequest req = investingRequestRepository.findInvestingRequestById(id);
        if(req==null){
            throw new ApiException("Request not found");
        }
        if (req.getStatus().equalsIgnoreCase("Accepted")) {
            throw new ApiException("Cannot reject an accepted request");
        }

        req.setStatus("Rejected");
        investingRequestRepository.save(req);
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




}
