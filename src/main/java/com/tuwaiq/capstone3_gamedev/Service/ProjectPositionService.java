package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.ProjectPosition;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectPositionRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectPositionService {

    private final ProjectPositionRepository projectPositionRepository;

    public void addProjectPosition(ProjectPosition projectPosition){
        projectPositionRepository.save(projectPosition);
    }

    public List<ProjectPosition> getProjectPositions(){
        return projectPositionRepository.findAll();
    }

    public void updateProjectPosition(Integer id, ProjectPosition projectPosition){
        ProjectPosition oldProjectPosition = projectPositionRepository.findProjectPositionById(id);
        if (oldProjectPosition == null){
            throw new ApiException("Project position not found");
        }
        oldProjectPosition.setAvailablePosition(projectPosition.getAvailablePosition());
        oldProjectPosition.setRequiredSkills(projectPosition.getRequiredSkills());
        oldProjectPosition.setDescription(projectPosition.getDescription());
        oldProjectPosition.setCompensationType(projectPosition.getCompensationType());
        oldProjectPosition.setCompensation(projectPosition.getCompensation());
        oldProjectPosition.setHoursPerWeek(projectPosition.getHoursPerWeek());
        projectPositionRepository.save(oldProjectPosition);
    }

    public void deleteProjectPosition(Integer id){
        ProjectPosition projectPosition = projectPositionRepository.findProjectPositionById(id);
        if (projectPosition == null){
            throw new ApiException("Project position not found");
        }
        projectPositionRepository.delete(projectPosition);
    }
}
