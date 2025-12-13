package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/get")
    public ResponseEntity<?> getProjects() {
        return ResponseEntity.status(200).body(projectService.getProjects());
    }

    @PostMapping("/add/{memberId}")
    public ResponseEntity<?> addProject(@PathVariable Integer memberId,@RequestBody @Valid Project project) {
        projectService.addProject(memberId, project);
        return ResponseEntity.status(200).body(new ApiResponse("project added"));
    }

    @PutMapping("/update/{memberId}/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Integer memberId,@PathVariable Integer projectId,@RequestBody @Valid Project project) {
        projectService.updateProject(memberId, projectId, project);
        return ResponseEntity.status(200).body(new ApiResponse("project updated"));
    }

    @DeleteMapping("/delete/{memberId}/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer memberId,@PathVariable Integer projectId) {
        projectService.deleteProject(memberId, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("project deleted"));
    }

    @PutMapping("/assign-genre/{leaderId}/{projectId}/{genreId}")
    public ResponseEntity<?> assignProjectToGenre(@PathVariable Integer leaderId,@PathVariable Integer projectId, @PathVariable Integer genreId){
        projectService.assignProjectToGenre(leaderId,projectId, genreId);
        return ResponseEntity.status(200).body(new ApiResponse("Project assigned to genre successfully"));
    }

    @PutMapping("/assign-platform/{leaderId}/{projectId}/{platformId}")
    public ResponseEntity<?> assignProjectToPlatform(@PathVariable Integer leaderId,@PathVariable Integer projectId, @PathVariable Integer platformId){
        projectService.assignProjectToPlatform(leaderId, projectId, platformId);
        return ResponseEntity.status(200).body(new ApiResponse("Project assigned to platform successfully"));
    }

    @PutMapping("/progress-project-status/{leaderId}/{projectId}")
    public ResponseEntity<?> progressProjectStatus(@PathVariable Integer leaderId,@PathVariable Integer projectId){
        return ResponseEntity.status(200).body(new ApiResponse("Project status have been move to: "+projectService.progressProjectStatus(leaderId,projectId)));
    }

    @GetMapping("/get-earnings-greater-than-x/{earningEstimation}")
    public ResponseEntity<?> getByEarning(@PathVariable Double earningEstimation) {
        return ResponseEntity.status(200).body(projectService.findProjectsByEarningEstimationGreaterThan(earningEstimation));
    }

    @GetMapping("/get-studio-id/{studioId}")
    public ResponseEntity<?> getByStudioId(@PathVariable Integer studioId) {
        return ResponseEntity.status(200).body(projectService.findProjectsByStudio_Id(studioId));
    }

    @GetMapping("/get-budget-greater-than-x/{budgetEstimation}")
    public ResponseEntity<?> getByBudget(@PathVariable Double budgetEstimation) {
        return ResponseEntity.status(200).body(projectService.findProjectsByBudgetEstimationGreaterThan(budgetEstimation));
    }

    @GetMapping("/get-by-scope/{scope}")
    public ResponseEntity<?> getByScope(@PathVariable String scope) {
        return ResponseEntity.status(200).body(projectService.findProjectsByScope(scope));
    }

    @GetMapping("/get-by-this-year")
    public ResponseEntity<?> getByThisYear() {
        return ResponseEntity.status(200).body(projectService.getThisYearProjects());
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        return ResponseEntity.status(200).body(projectService.findProjectsByStatus(status));
    }

    @GetMapping("/get-by-engine/{engine}")
    public ResponseEntity<?> getProjectsByEngine(@PathVariable String engine){
        return ResponseEntity.status(200).body(projectService.getProjectsByEngine(engine));
    }

    @GetMapping("/get-by-genre/{genre}")
    public ResponseEntity<?> getProjectsByGenre(@PathVariable String genre){
        return ResponseEntity.status(200).body(projectService.getProjectsByGenre(genre));
    }

    @GetMapping("/get-by-platform/{platform}")
    public ResponseEntity<?> getProjectsByPlatform(@PathVariable String platform){
        return ResponseEntity.status(200).body(projectService.getProjectsByPlatform(platform));
    }

    @GetMapping("/studio/{studioId}")
    public ResponseEntity<?> getFundedProjectsByStudioId(@PathVariable Integer studioId) {
        return ResponseEntity.status(200).body(projectService.getFundedProjectsByStudioId(studioId));
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<?> getFundedProjectsByInvestorId(@PathVariable Integer investorId) {
        return ResponseEntity.status(200).body(projectService.getFundedProjectsByInvestorId(investorId));
    }



}