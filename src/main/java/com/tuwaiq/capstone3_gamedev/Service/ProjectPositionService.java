package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOIn.ProjectPositionDTO;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.ProjectPosition;
import com.tuwaiq.capstone3_gamedev.Model.User;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectPositionRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import com.tuwaiq.capstone3_gamedev.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectPositionService {

    private final ProjectPositionRepository projectPositionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void addProjectPosition(Integer leaderId,ProjectPositionDTO projectPositionDTO){
        Project project=projectRepository.findProjectById(projectPositionDTO.getProjectId());
        if (project==null){
            throw new ApiException("Project not found");
        }
        User leader=userRepository.findUserById(leaderId);
        if (leader==null){
            throw new ApiException("User not found");
        }
        if (leader.getStudioMember()==null){
            throw new ApiException("This user doesn't belong to any studio");
        }
        if (!leader.getStudioMember().getRole().equalsIgnoreCase("leader")){
            throw new ApiException("You are not the leader, you don't have permissions to create project positions");
        }
        ProjectPosition projectPosition=new ProjectPosition(null, projectPositionDTO.getAvailablePosition(), projectPositionDTO.getRequiredSkills(), projectPositionDTO.getDescription(), projectPositionDTO.getCompensationType(), projectPositionDTO.getCompensation(), projectPositionDTO.getHoursPerWeek(),project,null,null);
        projectPositionRepository.save(projectPosition);
    }

    public List<ProjectPosition> getProjectPositions(){
        return projectPositionRepository.findAll();
    }

    public void updateProjectPosition(Integer leaderId,Integer id, ProjectPositionDTO projectPositionDTO){
        ProjectPosition oldProjectPosition = projectPositionRepository.findProjectPositionById(id);
        if (oldProjectPosition == null){
            throw new ApiException("Project position not found");
        }
        User leader=userRepository.findUserById(leaderId);
        if (leader==null){
            throw new ApiException("User not found");
        }
        if (leader.getStudioMember()==null){
            throw new ApiException("This user doesn't belong to any studio");
        }
        if (!leader.getStudioMember().getRole().equalsIgnoreCase("leader")){
            throw new ApiException("You are not the leader, you don't have permissions to update project positions");
        }
        oldProjectPosition.setAvailablePosition(projectPositionDTO.getAvailablePosition());
        oldProjectPosition.setRequiredSkills(projectPositionDTO.getRequiredSkills());
        oldProjectPosition.setDescription(projectPositionDTO.getDescription());
        oldProjectPosition.setCompensationType(projectPositionDTO.getCompensationType());
        oldProjectPosition.setCompensation(projectPositionDTO.getCompensation());
        oldProjectPosition.setHoursPerWeek(projectPositionDTO.getHoursPerWeek());
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
