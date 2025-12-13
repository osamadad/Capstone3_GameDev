package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOIn.ProjectMemberDTO;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.ProjectMember;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Model.User;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectMemberRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import com.tuwaiq.capstone3_gamedev.Repository.UserRepository;
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
    private final UserRepository userRepository;

    public void addProjectMember(Integer leaderId, ProjectMemberDTO projectMemberDTO){
        User user= userRepository.findUserById(projectMemberDTO.getUserId());
        if (user==null){
            throw new ApiException("User not found");
        }

        Project project=projectRepository.findProjectById(projectMemberDTO.getProjectId());
        if (project==null){
            throw new ApiException("Project not found");
        }
        StudioMember studioMember=studioMemberRepository.findStudioMemberById(user.getId());
        if (studioMember==null||!studioMember.getStudio().getId().equals(project.getStudio().getId())){
            throw new ApiException("This user doesn't belong to your studio");
        }
        checkStudioLeader(leaderId,project);

        ProjectMember projectMember=new ProjectMember();
        String compensationType=projectMemberDTO.getCompensationType();
        projectMember.setCompensationType(compensationType);
        Double compensation=projectMemberDTO.getCompensation();
        if (compensationType.equalsIgnoreCase("paid salary")){
            projectMember.setCompensationAmount(compensation);
        }else if (compensationType.equalsIgnoreCase("revenue share")){
            projectMember.setRevenueSharePercentage(compensation);
        }else {
            projectMember.setCompensationAmount(0.0);
        }
        projectMember.setHoursPerWeek(projectMemberDTO.getHoursPerWeek());
        projectMember.setCreatedAt(LocalDateTime.now());
        projectMember.setUser(user);
        projectMember.setProject(project);
        projectMemberRepository.save(projectMember);
    }

    public List<ProjectMember> getProjectMembers(){
        return projectMemberRepository.findAll();
    }

    public void updateProjectMember(Integer leaderId, Integer id, ProjectMember projectMember){
        ProjectMember oldProjectMember = projectMemberRepository.findProjectMemberById(id);
        if (oldProjectMember == null){
            throw new ApiException("Project member not found");
        }
        checkStudioLeader(leaderId, oldProjectMember.getProject());
        if (!(projectMember.getCompensationType().equalsIgnoreCase("free")||projectMember.getCompensationType().equalsIgnoreCase("paid salary")||projectMember.getCompensationType().equalsIgnoreCase("revenue share"))){
            throw new ApiException("Project compensation type must be 'free', 'revenue share', or 'paid salary'");
        }
        oldProjectMember.setCompensationType(projectMember.getCompensationType());
        oldProjectMember.setCompensationAmount(projectMember.getCompensationAmount());
        oldProjectMember.setRevenueSharePercentage(projectMember.getRevenueSharePercentage());
        oldProjectMember.setHoursPerWeek(projectMember.getHoursPerWeek());
        projectMemberRepository.save(oldProjectMember);
    }

    public void deleteProjectMember(Integer leaderId, Integer id){
        ProjectMember projectMember = projectMemberRepository.findProjectMemberById(id);
        if (projectMember == null){
            throw new ApiException("Project member not found");
        }
        checkStudioLeader(leaderId, projectMember.getProject());
        projectMemberRepository.delete(projectMember);
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
}
