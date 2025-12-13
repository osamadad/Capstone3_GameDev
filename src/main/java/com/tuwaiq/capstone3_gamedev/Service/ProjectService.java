package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Investor;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.Studio;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Repository.InvestorRepository;
import com.tuwaiq.capstone3_gamedev.Model.*;
import com.tuwaiq.capstone3_gamedev.Repository.GenreRepository;
import com.tuwaiq.capstone3_gamedev.Repository.PlatformRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StudioMemberRepository studioMemberRepository;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;
    private final StudioRepository studioRepository;
    private final InvestorRepository investorRepository;

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public void addProject(Integer memberId, Project project) {
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

        if (project.getStartDate().isAfter(project.getEndDate())) {
            throw new ApiException("Start date cannot be after end date");
        }

        project.setStatus("not started");
        project.setCreatedAt(LocalDateTime.now());
        project.setStudio(studio);
        projectRepository.save(project);
    }

    public void updateProject(Integer memberId, Integer projectId, Project project) {
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
        if (oldProject == null) {
            throw new ApiException("Project not found");
        }


        if (oldProject.getStudio() == null || !oldProject.getStudio().getId().equals(studio.getId())) {
            throw new ApiException("Project does not belong to your studio");
        }

        if (project.getStartDate().isAfter(project.getEndDate())) {
            throw new ApiException("Start date cannot be after end date");
        }

        oldProject.setName(project.getName());
        oldProject.setScope(project.getScope());
        oldProject.setDescription(project.getDescription());
        oldProject.setBudgetEstimation(project.getBudgetEstimation());
        oldProject.setEarningEstimation(project.getEarningEstimation());
        oldProject.setStartDate(project.getStartDate());
        oldProject.setEndDate(project.getEndDate());
        oldProject.setEngine(project.getEngine());
        projectRepository.save(oldProject);
    }

    public void deleteProject(Integer memberId, Integer projectId) {
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
        if (project == null) {
            throw new ApiException("Project not found");
        }

        if (project.getStudio() == null || !project.getStudio().getId().equals(studio.getId())) {
            throw new ApiException("Project does not belong to your studio");
        }

        projectRepository.delete(project);
    }

    //system endpoint
    public void assignProjectToGenre(Integer leaderId, Integer projectId, Integer genreId) {
        Project project = projectRepository.findProjectById(projectId);
        Genre genre = genreRepository.findGenreById(genreId);

        if (genre == null || project == null) {
            throw new ApiException("Project or genre not found");
        }

        checkStudioLeader(leaderId,project);

        project.getGenres().add(genre);
        genre.getProjects().add(project);
        projectRepository.save(project);
        genreRepository.save(genre);
    }

    //system endpoint
    public void assignProjectToPlatform(Integer leaderId, Integer projectId, Integer platformId) {
        Project project = projectRepository.findProjectById(projectId);
        Platform platform = platformRepository.findPlatformById(platformId);

        if (platform == null || project == null) {
            throw new ApiException("Project or platform not found");
        }

        checkStudioLeader(leaderId,project);

        project.getPlatforms().add(platform);
        platform.getProjects().add(project);
        projectRepository.save(project);
        platformRepository.save(platform);
    }

    public String  progressProjectStatus(Integer leaderId, Integer projectId){
        StudioMember member = studioMemberRepository.findStudioMemberById(leaderId);
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
        if (project == null) {
            throw new ApiException("Project not found");
        }

        if (project.getStudio() == null || !project.getStudio().getId().equals(studio.getId())) {
            throw new ApiException("Project does not belong to your studio");
        }

        if (project.getStatus().equalsIgnoreCase("not started")){
            project.setStatus("in Progress");
        }else if (project.getStatus().equalsIgnoreCase("in Progress")){
            project.setStatus("finished");
        }else{
            throw new ApiException("The project staus is already finished");
        }

        return project.getStatus();
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

    public List<Project> findProjectsByEarningEstimationGreaterThan(Double earningEstimation) {
        return projectRepository.findProjectsByEarningEstimationGreaterThan(earningEstimation);
    }

    public List<Project> findProjectsByBudgetEstimationGreaterThan(Double budgetEstimation) {
        return projectRepository.findProjectsByBudgetEstimationGreaterThan(budgetEstimation);
    }
    public List<Project> findProjectsByStudio_Id(Integer studioId) {
        return projectRepository.findProjectsByStudio_Id(studioId);
    }

    public List<Project> findProjectsByScope(String scope) {
        if (!scope.matches("^(game_jam|small|medium|big|AAA)$")) {
            throw new ApiException("Invalid scope value");
        }
        return projectRepository.findProjectsByScope(scope);
    }

    public List<Project> findProjectsByStatus(String status) {
        if (!status.matches("^(not started|in Progress|finished)$")) {
            throw new ApiException("Invalid status value");
        }
        return projectRepository.findProjectsByStatus(status);
    }

    public List<Project> getThisYearProjects(){
        return projectRepository.findThisYearProjects();
    }

    public List<Project> getProjectsByEngine(String engine){
        List<Project> projects = projectRepository.findProjectByEngine(engine);
        if (projects.isEmpty()){
            throw new ApiException("No projects found with the specified engine");
        }
        return projects;
    }

    public List<Project> getProjectsByGenre(String genre){
        List<Project> projects = projectRepository.findProjectByGenre(genre);
        if (projects.isEmpty()){
            throw new ApiException("No projects found for the specified genre");
        }
        return projects;
    }

    public List<Project> getProjectsByPlatform(String platform){
        List<Project> projects = projectRepository.findProjectByPlatforms(platform);
        if (projects.isEmpty()){
            throw new ApiException("No projects found for the specified platform");
        }
        return projects;
    }

    //Endpoints
//    public List<Project> getFundedProjectsByStudioId(Integer studioId) {
//        Studio studio = studioRepository.findStudioById(studioId);
//
//        if (studio == null) {
//            throw new ApiException("Studio not found");
//        }
//
//        List<Project> projects =
//                projectRepository.findAllByStudioIdAndInvestorIsNotNull(studioId);
//
//        if (projects.isEmpty()) {
//            throw new ApiException("No funded projects found for studio with id: "+studioId);
//        }
//
//        return projects;
//    }

//    public List<Project> getFundedProjectsByInvestorId(Integer investorId) {
//        Investor investor = investorRepository.findInvestorById(investorId);
//
//        if (investor == null) {
//            throw new ApiException("Investor not found");
//        }
//
//        List<Project> projects = projectRepository.findAllByInvestorId(investorId);
//
//        if (projects.isEmpty()) {
//            throw new ApiException("No funded projects found for investor with id: "+investorId);
//        }
//
//        return projects;
//    }



}