package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.DTOIn.ProjectMemberDTO;
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

    private final ProjectMemberService projectMemberService;

    @PostMapping("/add/{leaderId}")
    public ResponseEntity<?> addProjectMember(@PathVariable Integer leaderId, @RequestBody ProjectMemberDTO projectMemberDTO){
        projectMemberService.addProjectMember(leaderId, projectMemberDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Project member added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProjectMembers(){
        return ResponseEntity.status(200).body(projectMemberService.getProjectMembers());
    }

    @PutMapping("/update/{leaderId}/{projectMemberId}")
    public ResponseEntity<?> updateProjectMember(@PathVariable Integer leaderId,@PathVariable Integer projectMemberId, @RequestBody ProjectMember projectMember){
        projectMemberService.updateProjectMember(leaderId,projectMemberId, projectMember);
        return ResponseEntity.status(200).body(new ApiResponse("Project member updated successfully"));
    }

    @DeleteMapping("/delete/{leaderId}/{projectMemberId}")
    public ResponseEntity<?> deleteProjectMember(@PathVariable Integer leaderId,@PathVariable Integer projectMemberId){
        projectMemberService.deleteProjectMember(leaderId,projectMemberId);
        return ResponseEntity.status(200).body(new ApiResponse("Project member deleted successfully"));
    }
}
