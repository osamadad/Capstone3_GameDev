package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.Skill;
import com.tuwaiq.capstone3_gamedev.Service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/add")
    public ResponseEntity<?> addSkill(@RequestBody Skill skill){
        skillService.addSkill(skill);
        return ResponseEntity.status(200).body(new ApiResponse("Skill added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSkills(){
        return ResponseEntity.status(200).body(skillService.getSkills());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable Integer id, @RequestBody Skill skill){
        skillService.updateSkill(id, skill);
        return ResponseEntity.status(200).body(new ApiResponse("Skill updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Integer id){
        skillService.deleteSkill(id);
        return ResponseEntity.status(200).body(new ApiResponse("Skill deleted successfully"));
    }
}

