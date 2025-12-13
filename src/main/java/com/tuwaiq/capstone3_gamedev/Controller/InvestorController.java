package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.Investor;
import com.tuwaiq.capstone3_gamedev.Service.InvestorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/investor")
@AllArgsConstructor
public class InvestorController {
    private final InvestorService investorService;

    @GetMapping("/get")
    public List<Investor> getInvestors() {
        return investorService.getInvestors();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addInvestor(@RequestBody @Valid Investor investor) {
        investorService.addInvestor(investor);
        return ResponseEntity.status(200).body(new ApiResponse("Investor added successfully "));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInvestor(@PathVariable Integer id, @RequestBody @Valid Investor investor) {
        investorService.updateInvestor(id, investor);
        return ResponseEntity.status(200).body(new ApiResponse("Investor updated successfully "));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInvestor(@PathVariable Integer id) {
        investorService.deleteInvestor(id);
        return ResponseEntity.status(200).body(new ApiResponse("Investor deleted successfully "));
    }

    @GetMapping("/get-investor-with-most-funded-project")
    public ResponseEntity<?> getInvestorWithMostFundedProjects(){
        return ResponseEntity.status(200).body(investorService.getInvestorWithMostFundedProject());
    }
}
