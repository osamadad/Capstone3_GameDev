package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.Studio;
import com.tuwaiq.capstone3_gamedev.Service.StudioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/studio")
@RequiredArgsConstructor
public class StudioController {

    private final StudioService studioService;

    @GetMapping("/get")
    public ResponseEntity<?> getStudio() {
        return ResponseEntity.status(200).body(studioService.getStudio());
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> getStudio(@PathVariable Integer userId, @RequestBody @Valid Studio studio) {
        studioService.addStudio(userId, studio);
        return ResponseEntity.status(200).body(new ApiResponse("studio added"));
    }

    @PutMapping("/update/{studioMemberId}/{id}")
    public ResponseEntity<?> getStudio(@PathVariable Integer studioMemberId, @PathVariable Integer id,@RequestBody @Valid Studio studio) {
        studioService.updateStudio(studioMemberId, id, studio);
        return ResponseEntity.status(200).body(new ApiResponse("studio updated"));
    }

    @DeleteMapping("/delete/{studioMemberId}/{id}")
    public ResponseEntity<?> getStudio(@PathVariable Integer studioMemberId, @PathVariable Integer id) {
        studioService.deleteStudio(studioMemberId, id);
        return ResponseEntity.status(200).body(new ApiResponse("studio deleted"));
    }
    @GetMapping("/get-most-project")
    public ResponseEntity<?> getByMostProjects() {
        return ResponseEntity.status(200).body(studioService.getStudiosByMostProjects());
    }

}
