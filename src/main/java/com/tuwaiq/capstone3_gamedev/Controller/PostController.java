package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.Post;
import com.tuwaiq.capstone3_gamedev.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(postService.get());
    }
    @PostMapping("/add/{memberId}")
    public ResponseEntity<?> add(@PathVariable Integer memberId,@RequestBody @Valid Post post) {
        postService.add(memberId, post);
        return ResponseEntity.status(200).body(new ApiResponse("post added"));
    }

    @PutMapping("/update/{memberId}/{postId}")
    public ResponseEntity<?> update(@PathVariable Integer memberId,@PathVariable Integer postId,@RequestBody @Valid Post post) {
        postService.update(memberId, postId, post);
        return ResponseEntity.status(200).body(new ApiResponse("post updated"));
    }

    @DeleteMapping("/delete/{memberId}/{postId}")
    public ResponseEntity<?> delete(@PathVariable Integer memberId,@PathVariable Integer postId) {
        postService.delete(memberId, postId);
        return ResponseEntity.status(200).body(new ApiResponse("post deleted"));
    }
}