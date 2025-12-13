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
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.status(200).body(postService.getPosts());
    }

    @PostMapping("/add/{memberId}")
    public ResponseEntity<?> addPost(@PathVariable Integer memberId,@RequestBody @Valid Post post) {
        postService.addPost(memberId, post);
        return ResponseEntity.status(200).body(new ApiResponse("post added"));
    }

    @PutMapping("/update/{memberId}/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Integer memberId,@PathVariable Integer postId,@RequestBody @Valid Post post) {
        postService.updatePost(memberId, postId, post);
        return ResponseEntity.status(200).body(new ApiResponse("post updated"));
    }

    @DeleteMapping("/delete/{memberId}/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer memberId,@PathVariable Integer postId) {
        postService.deletePost(memberId, postId);
        return ResponseEntity.status(200).body(new ApiResponse("post deleted"));
    }
}