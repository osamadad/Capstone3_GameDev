package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Service.StudioMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/studiomember")
@RequiredArgsConstructor
public class StudioMemberController {

    private final StudioMemberService studioMemberService;

    @GetMapping("get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(studioMemberService.get());
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody @Valid StudioMember studioMember) {
        studioMemberService.add(studioMember);
        return ResponseEntity.status(200).body(new ApiResponse("studio member added"));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid StudioMember studioMember) {
        studioMemberService.update(id, studioMember);
        return ResponseEntity.status(200).body(new ApiResponse("studio member updated"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        studioMemberService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("studio member deleted"));
    }
}
