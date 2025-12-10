package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.DTOIn.UserRequestDTO;
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
    public ResponseEntity<?> add(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userRequestService.add(userRequestDTO);
        return ResponseEntity.status(200).body("User request added successfully");
    }


    @PutMapping("/update/{userId}/{requestId}")
    public ResponseEntity<?> update(@PathVariable Integer userId,@PathVariable Integer requestId, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        userRequestService.update(userId,requestId, userRequestDTO);
        return ResponseEntity.status(200).body("User request updated successfully");
    }


    @DeleteMapping("/delete/{userId}/{requestId}")
    public ResponseEntity<?> delete(@PathVariable Integer userId,@PathVariable Integer requestId) {
        userRequestService.delete(userId,requestId);
        return ResponseEntity.status(200).body("User request deleted successfully");
    }


    //Endpoints
    @PutMapping("/accept/{leaderId}/{requestId}")
    public ResponseEntity<?> accept(@PathVariable Integer leaderId,@PathVariable Integer requestId) {
        userRequestService.acceptRequest(leaderId,requestId);
        return ResponseEntity.status(200).body("User request accepted");
    }


    @PutMapping("/reject/{leaderId}/{requestId}")
    public ResponseEntity<?> reject(@PathVariable Integer leaderId,@PathVariable Integer requestId) {
        userRequestService.rejectRequest(leaderId,requestId);
        return ResponseEntity.status(200).body("User request rejected");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRequestsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(userRequestService.getUserRequestsByUserId(userId));
    }

//    @GetMapping("/studio/{studioId}")
//    public ResponseEntity<?> getRequestsByStudioId(@PathVariable Integer studioId) {
//        return ResponseEntity.status(200).body(userRequestService.getRequestsByStudioId(studioId));
//    }


}
