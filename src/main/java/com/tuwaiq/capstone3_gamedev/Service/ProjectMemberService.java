package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.ProjectMember;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectMemberRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final StudioMemberRepository studioMemberRepository;
    private final ProjectRepository projectRepository;

    public void addProjectMember(ProjectMember projectMember){
        projectMember.setCreated_at(LocalDateTime.now());
        projectMemberRepository.save(projectMember);
    }

    public List<ProjectMember> getProjectMembers(){
        return projectMemberRepository.findAll();
    }

    public void updateProjectMember(Integer id, ProjectMember projectMember){
        ProjectMember oldProjectMember = projectMemberRepository.findProjectMemberById(id);
        if (oldProjectMember == null){
            throw new ApiException("Project member not found");
        }
        if (!(projectMember.getCompensationType().equalsIgnoreCase("free")||projectMember.getCompensationType().equalsIgnoreCase("paid salary")||projectMember.getCompensationType().equalsIgnoreCase("revenue share"))){
            throw new ApiException("Project compensation type must be 'free', 'revenue share', or 'paid salary'");
        }
        oldProjectMember.setCompensationType(projectMember.getCompensationType());
        oldProjectMember.setCompensationAmount(projectMember.getCompensationAmount());
        oldProjectMember.setRevenueSharePercentage(projectMember.getRevenueSharePercentage());
        oldProjectMember.setHoursPerWeek(projectMember.getHoursPerWeek());
        projectMemberRepository.save(oldProjectMember);
    }

    public void deleteProjectMember(Integer id){
        ProjectMember projectMember = projectMemberRepository.findProjectMemberById(id);
        if (projectMember == null){
            throw new ApiException("Project member not found");
        }
        projectMemberRepository.delete(projectMember);
    }

}
