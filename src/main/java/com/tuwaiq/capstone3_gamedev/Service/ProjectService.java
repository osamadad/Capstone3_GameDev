package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.Studio;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StudioMemberRepository studioMemberRepository;

    public List<Project> get() {
        return projectRepository.findAll();
    }

    public void add(Integer memberId, Project project) {
        StudioMember member = studioMemberRepository.findStudioMemberById(memberId);
        if (member == null) {
            throw new ApiException("Member not found");
        }

        if (!"leader".equalsIgnoreCase(member.getRole())) {
            throw new ApiException("Only leaders can create projects");
        }

        Studio studio = member.getStudio();
        if (studio == null) {
            throw new ApiException("Member does not belong to any studio");
        }

        if (project.getStart_date().isAfter(project.getEnd_date())) {
            throw new ApiException("Start date cannot be after end date");
        }

        project.setCreated_at(LocalDateTime.now());
        project.setStudio(studio);
        projectRepository.save(project);
    }

    public void update(Integer memberId, Integer projectId, Project project) {
        StudioMember member = studioMemberRepository.findStudioMemberById(memberId);
        if (member == null) {
            throw new ApiException("Member not found");
        }

        if (!"leader".equalsIgnoreCase(member.getRole())) {
            throw new ApiException("Only leaders can update projects");
        }

        Studio studio = member.getStudio();
        if (studio == null) {
            throw new ApiException("Member does not belong to any studio");
        }

        Project oldProject = projectRepository.findProjectById(projectId);
        if (oldProject == null){
            throw new ApiException("Project not found");
        }


        if (oldProject.getStudio() == null || !oldProject.getStudio().getId().equals(studio.getId())) {
            throw new ApiException("Project does not belong to your studio");
        }

        if (project.getStart_date().isAfter(project.getEnd_date())) {
            throw new ApiException("Start date cannot be after end date");
        }

        oldProject.setName(project.getName());
        oldProject.setScope(project.getScope());
        oldProject.setDescription(project.getDescription());
        oldProject.setBudget_estimation(project.getBudget_estimation());
        oldProject.setEarning_estimation(project.getEarning_estimation());
        oldProject.setStart_date(project.getStart_date());
        oldProject.setEnd_date(project.getEnd_date());
        oldProject.setStatus(project.getStatus());

        projectRepository.save(oldProject);
    }

    public void delete(Integer memberId, Integer projectId) {
        StudioMember member = studioMemberRepository.findStudioMemberById(memberId);
        if (member == null) {
            throw new ApiException("Member not found");
        }

        if (!"leader".equalsIgnoreCase(member.getRole())) {
            throw new ApiException("Only leaders can delete projects");
        }

        Studio studio = member.getStudio();
        if (studio == null) {
            throw new ApiException("Member does not belong to any studio");
        }

        Project project = projectRepository.findProjectById(projectId);
        if (project == null){
            throw new ApiException("Project not found");
        }

        if (project.getStudio() == null || !project.getStudio().getId().equals(studio.getId())) {
            throw new ApiException("Project does not belong to your studio");
        }

        projectRepository.delete(project);
    }

}