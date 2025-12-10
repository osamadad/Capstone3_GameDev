package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
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

    @PostMapping("/add")
    public ResponseEntity<?> addProjectPosition(@RequestBody ProjectPosition projectPosition){
        projectPositionService.addProjectPosition(projectPosition);
        return ResponseEntity.status(200).body(new ApiResponse("Project position added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProjectPositions(){
        return ResponseEntity.status(200).body(projectPositionService.getProjectPositions());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProjectPosition(@PathVariable Integer id, @RequestBody ProjectPosition projectPosition){
        projectPositionService.updateProjectPosition(id, projectPosition);
        return ResponseEntity.status(200).body(new ApiResponse("Project position updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProjectPosition(@PathVariable Integer id){
        projectPositionService.deleteProjectPosition(id);
        return ResponseEntity.status(200).body(new ApiResponse("Project position deleted successfully"));
    }
}
