package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOIn.ProjectPositionDTO;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.ProjectPosition;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Model.User;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectPositionRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import com.tuwaiq.capstone3_gamedev.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectPositionService {

    private final ProjectPositionRepository projectPositionRepository;
    private final ProjectRepository projectRepository;
    private final StudioMemberRepository studioMemberRepository;

    public void addProjectPosition(Integer leaderId,ProjectPositionDTO projectPositionDTO){
        Project project=projectRepository.findProjectById(projectPositionDTO.getProjectId());
        if (project==null){
            throw new ApiException("Project not found");
        }
        checkStudioLeader(leaderId, project);
        ProjectPosition projectPosition=new ProjectPosition(null, projectPositionDTO.getAvailablePosition(), projectPositionDTO.getRequiredSkills(), projectPositionDTO.getDescription(), projectPositionDTO.getCompensationType(), projectPositionDTO.getCompensation(), projectPositionDTO.getHoursPerWeek(), LocalDateTime.now(),project,null,null);
        projectPositionRepository.save(projectPosition);
    }

    public List<ProjectPosition> getProjectPositions(){
        return projectPositionRepository.findAll();
    }

    public void updateProjectPosition(Integer leaderId,Integer projectPositionId, ProjectPositionDTO projectPositionDTO){
        ProjectPosition oldProjectPosition = projectPositionRepository.findProjectPositionById(projectPositionId);
        if (oldProjectPosition == null){
            throw new ApiException("Project position not found");
        }
        checkStudioLeader(leaderId, oldProjectPosition.getProject());
        oldProjectPosition.setAvailablePosition(projectPositionDTO.getAvailablePosition());
        oldProjectPosition.setRequiredSkills(projectPositionDTO.getRequiredSkills());
        oldProjectPosition.setDescription(projectPositionDTO.getDescription());
        oldProjectPosition.setCompensationType(projectPositionDTO.getCompensationType());
        oldProjectPosition.setCompensation(projectPositionDTO.getCompensation());
        oldProjectPosition.setHoursPerWeek(projectPositionDTO.getHoursPerWeek());
        projectPositionRepository.save(oldProjectPosition);
    }

    public void deleteProjectPosition(Integer leaderId, Integer projectPositionId){
        ProjectPosition projectPosition = projectPositionRepository.findProjectPositionById(projectPositionId);
        if (projectPosition == null){
            throw new ApiException("Project position not found");
        }
        checkStudioLeader(leaderId, projectPosition.getProject());
        projectPositionRepository.delete(projectPosition);
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

    public List<ProjectPosition> getProjectPositionsByProjectId(Integer projectId){
        List<ProjectPosition> projectPositions=projectPositionRepository.findProjectPositionByProject_Id(projectId);
        if (projectPositions.isEmpty()){
            throw new ApiException("There are no available positions for this project");
        }
        return projectPositions;
    }
}
