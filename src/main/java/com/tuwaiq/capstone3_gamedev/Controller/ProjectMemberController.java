package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.ProjectMember;
import com.tuwaiq.capstone3_gamedev.Service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project-member")
@RequiredArgsConstructor
public class ProjectMemberController {

    private ProjectMemberService projectMemberService;

    @PostMapping("/add")
    public ResponseEntity<?> addProjectMember(@RequestBody ProjectMember projectMember){
        projectMemberService.addProjectMember(projectMember);
        return ResponseEntity.status(200).body(new ApiResponse("Project member added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProjectMembers(){
        return ResponseEntity.status(200).body(projectMemberService.getProjectMembers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProjectMember(@PathVariable Integer id, @RequestBody ProjectMember projectMember){
        projectMemberService.updateProjectMember(id, projectMember);
        return ResponseEntity.status(200).body(new ApiResponse("Project member updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProjectMember(@PathVariable Integer id){
        projectMemberService.deleteProjectMember(id);
        return ResponseEntity.status(200).body(new ApiResponse("Project member deleted successfully"));
    }
}
