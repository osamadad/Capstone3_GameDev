package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Model.UserRequest;
import com.tuwaiq.capstone3_gamedev.Service.UserRequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-request")
@AllArgsConstructor
public class UserRequestController {
    private final UserRequestService userRequestService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(userRequestService.getAll());
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid UserRequest request) {
        userRequestService.add(request);
        return ResponseEntity.status(200).body("User request added successfully");
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid UserRequest request) {
        userRequestService.update(id, request);
        return ResponseEntity.status(200).body("User request updated successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        userRequestService.delete(id);
        return ResponseEntity.status(200).body("User request deleted successfully");
    }


    //Endpoints
    @PutMapping("/accept/{id}")
    public ResponseEntity<?> accept(@PathVariable Integer id) {
        userRequestService.acceptRequest(id);
        return ResponseEntity.status(200).body("User request accepted");
    }


    @PutMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Integer id) {
        userRequestService.rejectRequest(id);
        return ResponseEntity.status(200).body("User request rejected");
    }
}
