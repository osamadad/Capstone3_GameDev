package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.ProjectInvestor;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectInvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectInvestorService {

    private final ProjectInvestorRepository projectInvestorRepository;

    public void addProjectInvestor(ProjectInvestor projectInvestor){
        projectInvestor.setCreated_at(LocalDateTime.now());
        projectInvestorRepository.save(projectInvestor);
    }

    public List<ProjectInvestor> getProjectInvestors(){
        return projectInvestorRepository.findAll();
    }

    public void updateProjectInvestor(Integer id, ProjectInvestor projectInvestor){
        ProjectInvestor oldInvestor = projectInvestorRepository.findProjectInvestorById(id);

        if (oldInvestor == null){
            throw new ApiException("Project investor not found");
        }

        oldInvestor.setInvestedAmount(projectInvestor.getInvestedAmount());
        oldInvestor.setEquityShare(projectInvestor.getEquityShare());

        projectInvestorRepository.save(oldInvestor);
    }

    public void deleteProjectInvestor(Integer id){
        ProjectInvestor investor = projectInvestorRepository.findProjectInvestorById(id);

        if (investor == null){
            throw new ApiException("Project investor not found");
        }

        projectInvestorRepository.delete(investor);
    }
}
