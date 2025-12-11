package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.DTOIn.ProjectRequestDTO;
import com.tuwaiq.capstone3_gamedev.Model.ProjectRequest;
import com.tuwaiq.capstone3_gamedev.Service.ProjectRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project-request")
@RequiredArgsConstructor
public class ProjectRequestController {

    private final ProjectRequestService projectRequestService;

    @PostMapping("/add")
    public ResponseEntity<?> addProjectRequest(@RequestBody ProjectRequestDTO projectRequestDTO){
        projectRequestService.addProjectRequest(projectRequestDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Project request added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProjectRequests(){
        return ResponseEntity.status(200).body(projectRequestService.getProjectRequests());
    }

    @DeleteMapping("/delete/{userId}/{projectRequestId}")
    public ResponseEntity<?> deleteProjectRequest(@PathVariable Integer userId, @PathVariable Integer projectRequestId){
        projectRequestService.deleteProjectRequest(userId,projectRequestId);
        return ResponseEntity.status(200).body(new ApiResponse("Project request deleted successfully"));
    }

    @PutMapping("/accept-project-request/{userId}/{projectRequestId}")
    public ResponseEntity<?> acceptProjectRequest(@PathVariable Integer userId, @PathVariable Integer projectRequestId){
        projectRequestService.acceptProjectRequest(userId, projectRequestId);
        return ResponseEntity.status(200).body(new ApiResponse("Project request accepted successfully"));
    }

    @PutMapping("/reject-project-request/{userId}/{projectRequestId}")
    public ResponseEntity<?> rejectProjectRequest(@PathVariable Integer userId, @PathVariable Integer projectRequestId){
        projectRequestService.rejectRequest(userId, projectRequestId);
        return ResponseEntity.status(200).body(new ApiResponse("Project request rejected successfully"));
    }
}
