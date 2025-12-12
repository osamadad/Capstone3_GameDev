package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.ProjectInvestor;
import com.tuwaiq.capstone3_gamedev.Service.ProjectInvestorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project-investor")
@RequiredArgsConstructor
public class ProjectInvestorController {

    private final ProjectInvestorService projectInvestorService;

    @PostMapping("/add")
    public ResponseEntity<?> addProjectInvestor(@RequestBody ProjectInvestor projectInvestor){
        projectInvestorService.addProjectInvestor(projectInvestor);
        return ResponseEntity.status(200).body(new ApiResponse("Project investor added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProjectInvestors(){
        return ResponseEntity.status(200).body(projectInvestorService.getProjectInvestors());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProjectInvestor(@PathVariable Integer id, @RequestBody ProjectInvestor projectInvestor){
        projectInvestorService.updateProjectInvestor(id, projectInvestor);
        return ResponseEntity.status(200).body(new ApiResponse("Project investor updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProjectInvestor(@PathVariable Integer id){
        projectInvestorService.deleteProjectInvestor(id);
        return ResponseEntity.status(200).body(new ApiResponse("Project investor deleted successfully"));
    }
}
