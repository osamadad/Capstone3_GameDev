package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.DTOIn.InvestingRequestDTO;
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
    public ResponseEntity<?> getInvestingRequests() {
        return ResponseEntity.status(200).body(investingRequestService.getInvestingRequests());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addInvestingRequest(@RequestBody @Valid InvestingRequestDTO investingRequestDTO) {
        investingRequestService.addInvestingRequest(investingRequestDTO);
        return ResponseEntity.status(200).body("Investing request added successfully");
    }


    @PutMapping("/update/{investorId}/{requestId}")
    public ResponseEntity<?> updateInvestingRequest(@PathVariable Integer investorId,@PathVariable Integer requestId, @RequestBody @Valid InvestingRequestDTO investingRequestDTO) {
        investingRequestService.updateInvestingRequest(investorId,requestId, investingRequestDTO);
        return ResponseEntity.status(200).body("Investing request updated successfully");
    }


    @DeleteMapping("/delete/{investorId}/{requestId}")
    public ResponseEntity<?> deleteInvestingRequest(@PathVariable Integer investorId,@PathVariable Integer requestId) {
        investingRequestService.deleteInvestingRequest(investorId,requestId);
        return ResponseEntity.status(200).body("Investing request deleted successfully");
    }

    //System endpoint
    @PutMapping("/accept/{leaderId}/{requestId}")
    public ResponseEntity<?> acceptRequest(@PathVariable Integer leaderId,@PathVariable Integer requestId) {
        investingRequestService.acceptRequest(leaderId,requestId);
        return ResponseEntity.status(200).body("Investing request accepted");
    }

    //System endpoint
    @PutMapping("/reject/{leaderId}/{requestId}")
    public ResponseEntity<?> rejectRequest(@PathVariable Integer leaderId,@PathVariable Integer requestId) {
        investingRequestService.rejectRequest(leaderId,requestId);
        return ResponseEntity.status(200).body("Investing request rejected");
    }

    //End points
    @GetMapping("/investor/{investorId}")
    public ResponseEntity<?> getRequestsByInvestorId(@PathVariable Integer investorId) {
        return ResponseEntity.status(200).body(investingRequestService.getRequestsByInvestorId(investorId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getRequestsByProjectId(@PathVariable Integer projectId) {
        return ResponseEntity.status(200).body(investingRequestService.getRequestsByProjectId(projectId));
    }


}
