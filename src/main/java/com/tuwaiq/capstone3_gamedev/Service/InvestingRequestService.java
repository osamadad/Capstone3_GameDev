package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.InvestingRequest;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Repository.InvestingRequestRepository;
import com.tuwaiq.capstone3_gamedev.Repository.InvestorRepository;
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

    public List<InvestingRequest> getAll() {
        return investingRequestRepository.findAll();
    }


    public void add(InvestingRequest request) {
        Integer investorId = request.getInvestor().getId();

        if (!investorRepository.existsById(investorId)) {
            throw new ApiException("Investor not found");
        }


        Integer projectId = request.getProject().getId();
        if (!projectRepository.existsById(projectId)) {
            throw new ApiException("Project not found");
        }

        boolean alreadyRequested = investingRequestRepository.existsByInvestorIdAndProjectId(investorId, projectId);

        if (alreadyRequested) {
            throw new ApiException("Investor already has a request for this project");
        }

        request.setStatus("Pending");
        request.setCreatedAt(LocalDateTime.now());
        investingRequestRepository.save(request);
    }


    public void update(Integer id, InvestingRequest request) {
        InvestingRequest old = investingRequestRepository.findInvestingRequestById(id);
        if(old==null){
            throw new ApiException("Request not found");
        }

        old.setOffer(request.getOffer());
        investingRequestRepository.save(old);
    }


    public void delete(Integer id) {
        InvestingRequest old = investingRequestRepository.findInvestingRequestById(id);
        if(old==null){
            throw new ApiException("Request not found");
        }
        investingRequestRepository.delete(old);
    }


    //Endpoints
    public void acceptRequest(Integer id) {
        InvestingRequest req = investingRequestRepository.findInvestingRequestById(id);
        if(req==null){
            throw new ApiException("Request not found");
        }
        if (req.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("Cannot accept a rejected request");
        }

        Project project = req.getProject();
        project.setInvestor(req.getInvestor());
        projectRepository.save(project);


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


}
