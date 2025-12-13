package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.DTOIn.ProjectPositionDTO;
import com.tuwaiq.capstone3_gamedev.Model.ProjectPosition;
import com.tuwaiq.capstone3_gamedev.Service.ProjectPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/project-position")
@RequiredArgsConstructor
public class ProjectPositionController {

    private final ProjectPositionService projectPositionService;

    @PostMapping("/add/{leaderId}")
    public ResponseEntity<?> addProjectPosition(@PathVariable Integer leaderId,@RequestBody ProjectPositionDTO projectPositionDTO){
        projectPositionService.addProjectPosition(leaderId,projectPositionDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Project position added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProjectPositions(){
        return ResponseEntity.status(200).body(projectPositionService.getProjectPositions());
    }

    @PutMapping("/update/{leaderId}/{positionId}")
    public ResponseEntity<?> updateProjectPosition(@PathVariable Integer leaderId, @PathVariable Integer positionId, @RequestBody ProjectPositionDTO projectPositionDTO){
        projectPositionService.updateProjectPosition(leaderId,positionId, projectPositionDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Project position updated successfully"));
    }

    @DeleteMapping("/delete//{leaderId}/{positionId}")
    public ResponseEntity<?> deleteProjectPosition(@PathVariable Integer leaderId,@PathVariable Integer positionId){
        projectPositionService.deleteProjectPosition(leaderId,positionId);
        return ResponseEntity.status(200).body(new ApiResponse("Project position deleted successfully"));
    }

    @GetMapping("/get-by-project-id/{projectId}")
    public ResponseEntity<?> getProjectPositionsByProjectId(@PathVariable Integer projectId){
        return ResponseEntity.status(200).body(projectPositionService.getProjectPositionsByProjectId(projectId));
    }

}
