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

    @GetMapping("get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(studioService.get());
    }

    @PostMapping("add/{studioMemberId}")
    public ResponseEntity<?> add(@PathVariable Integer studioMemberId, @RequestBody @Valid Studio studio) {
        studioService.add(studioMemberId, studio);
        return ResponseEntity.status(200).body(new ApiResponse("studio added"));
    }

    @PutMapping("update/{studioMemberId}/{id}")
    public ResponseEntity<?> update(@PathVariable Integer studioMemberId, @PathVariable Integer id,@RequestBody @Valid Studio studio) {
        studioService.update(studioMemberId, id, studio);
        return ResponseEntity.status(200).body(new ApiResponse("studio updated"));
    }

    @DeleteMapping("delete/{studioMemberId}/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer studioMemberId, @PathVariable Integer id) {
        studioService.delete(studioMemberId, id);
        return ResponseEntity.status(200).body(new ApiResponse("studio deleted"));
    }
}
