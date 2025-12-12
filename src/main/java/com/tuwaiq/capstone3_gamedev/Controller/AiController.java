package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.DTO.ProjectSuccessPredictionDTO;
import com.tuwaiq.capstone3_gamedev.Service.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@AllArgsConstructor
public class AiController {

    private final OpenAIService openAIService;

    @GetMapping("/studio-performance/{studioId}")
    public ResponseEntity<?> analyzeStudio(@PathVariable Integer studioId) {
        return ResponseEntity.status(200).body(openAIService.analyzeStudio(studioId));
    }

    @GetMapping("/studios-for-user/{userId}")
    public ResponseEntity<?> suggestStudiosForUser(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(openAIService.suggestStudiosForUser(userId));
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<?> predictProjectSuccess(@PathVariable Integer projectId) {
        return ResponseEntity.status(200).body(openAIService.predictProjectSuccess(projectId));
    }

}

