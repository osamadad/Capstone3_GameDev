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
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(projectService.get());
    }

    @PostMapping("/add/{memberId}")
    public ResponseEntity<?> add(@PathVariable Integer memberId,@RequestBody @Valid Project project) {
        projectService.add(memberId, project);
        return ResponseEntity.status(200).body(new ApiResponse("project added"));
    }

    @PutMapping("/update/{memberId}/{projectId}")
    public ResponseEntity<?> update(@PathVariable Integer memberId,@PathVariable Integer projectId,@RequestBody @Valid Project project) {
        projectService.update(memberId, projectId, project);
        return ResponseEntity.status(200).body(new ApiResponse("project updated"));
    }

    @DeleteMapping("/delete/{memberId}/{projectId}")
    public ResponseEntity<?> delete(@PathVariable Integer memberId,@PathVariable Integer projectId) {
        projectService.delete(memberId, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("project deleted"));
    }

    @PutMapping("/assign-genre/{projectId}/{genreId}")
    public ResponseEntity<?> assignProjectToGenre(@PathVariable Integer projectId, @PathVariable Integer genreId){
        projectService.assignProjectToGenre(projectId, genreId);
        return ResponseEntity.status(200).body(new ApiResponse("Project assigned to genre successfully"));
    }

    @PutMapping("/assign-platform/{projectId}/{platformId}")
    public ResponseEntity<?> assignProjectToPlatform(@PathVariable Integer projectId, @PathVariable Integer platformId){
        projectService.assignProjectToPlatform(projectId, platformId);
        return ResponseEntity.status(200).body(new ApiResponse("Project assigned to platform successfully"));
    }

    @GetMapping("/get-earnings-greater-than-x/{earningEstimation}")
    public ResponseEntity<?> geByEarning(@PathVariable Double earningEstimation) {
        return ResponseEntity.status(200).body(projectService.findProjectsByEarningEstimationGreaterThan(earningEstimation));
    }

    @GetMapping("/get-studio-id/{studioId}")
    public ResponseEntity<?> getByStudioId(@PathVariable Integer studioId) {
        return ResponseEntity.status(200).body(projectService.findProjectsByStudio_Id(studioId));
    }

    @GetMapping("/get-budget-greater-than-x/{budgetEstimation}")
    public ResponseEntity<?> geByBudget(@PathVariable Double budgetEstimation) {
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

}