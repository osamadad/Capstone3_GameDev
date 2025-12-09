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

}