package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Post;
import com.tuwaiq.capstone3_gamedev.Model.Studio;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Repository.PostRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final StudioMemberRepository studioMemberRepository;

    public List<Post> get() {
        return postRepository.findAll();
    }

    public void add(Integer memberId, Post post) {
        StudioMember member = studioMemberRepository.findStudioMemberById(memberId);
        if (member == null) {
            throw new ApiException("Member not found");
        }

        if (!"leader".equalsIgnoreCase(member.getRole())) {
            throw new ApiException("Only leaders can create posts");
        }

        Studio studio = member.getStudio();
        if (studio == null) {
            throw new ApiException("Member does not belong to any studio");
        }

        post.setCreated_at(LocalDateTime.now());
        post.setStudio(studio);
        postRepository.save(post);
    }

    public void update(Integer memberId, Integer postId, Post post) {
        StudioMember member = studioMemberRepository.findStudioMemberById(memberId);
        if (member == null) {
            throw new ApiException("Member not found");
        }

        if (!"leader".equalsIgnoreCase(member.getRole())) {
            throw new ApiException("Only leaders can update posts");
        }

        Studio studio = member.getStudio();
        if (studio == null) {
            throw new ApiException("Member does not belong to any studio");
        }

        Post oldPost = postRepository.findPostById(postId);
        if (post == null) {
            throw new ApiException("Post not found");
        }

        if (oldPost.getStudio() == null || !oldPost.getStudio().getId().equals(studio.getId())) {
            throw new ApiException("Post does not belong to your studio");
        }

        oldPost.setTitle(post.getTitle());
        oldPost.setDescription(post.getDescription());
        oldPost.setType(post.getType());
        oldPost.setMedia_url(post.getMedia_url());

        postRepository.save(oldPost);
    }

    public void delete(Integer memberId, Integer postId) {
        StudioMember member = studioMemberRepository.findStudioMemberById(memberId);
        if (member == null) {
            throw new ApiException("Member not found");
        }

        if (!"leader".equalsIgnoreCase(member.getRole())) {
            throw new ApiException("Only leaders can delete posts");
        }

        Studio studio = member.getStudio();
        if (studio == null) {
            throw new ApiException("Member does not belong to any studio");
        }

        Post post = postRepository.findPostById(postId);
        if (post == null) {
            throw new ApiException("Post not found");
        }

        if (post.getStudio() == null || !post.getStudio().getId().equals(studio.getId())) {
            throw new ApiException("Post does not belong to your studio");
        }

        postRepository.delete(post);
    }
}