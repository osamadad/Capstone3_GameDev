package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Model.InvestingRequest;
import com.tuwaiq.capstone3_gamedev.Service.InvestingRequestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/investing-requests")
@AllArgsConstructor
public class InvestingRequestController {
    private final InvestingRequestService investingRequestService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(investingRequestService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid InvestingRequest request) {
        investingRequestService.add(request);
        return ResponseEntity.status(200).body("Investing request added successfully");
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid InvestingRequest request) {
        investingRequestService.update(id, request);
        return ResponseEntity.status(200).body("Investing request updated successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        investingRequestService.delete(id);
        return ResponseEntity.status(200).body("Investing request deleted successfully");
    }

    //Endpoints
    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptRequest(@PathVariable Integer id) {
        investingRequestService.acceptRequest(id);
        return ResponseEntity.status(200).body("Investing request accepted");
    }


    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectRequest(@PathVariable Integer id) {
        investingRequestService.rejectRequest(id);
        return ResponseEntity.status(200).body("Investing request rejected");
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<?> getRequestsByInvestorId(@PathVariable Integer investorId) {
        return ResponseEntity.status(200).body(investingRequestService.getRequestsByInvestorId(investorId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getRequestsByProjectId(@PathVariable Integer projectId) {
        return ResponseEntity.status(200).body(investingRequestService.getRequestsByProjectId(projectId));
    }


}
