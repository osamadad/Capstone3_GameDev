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

    @GetMapping
    public List<Investor> getAll() {
        return investorService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid Investor investor) {
        investorService.add(investor);
        return ResponseEntity.status(200).body(new ApiResponse("Investor added successfully "));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Investor investor) {
        investorService.update(id, investor);
        return ResponseEntity.status(200).body(new ApiResponse("Investor updated successfully "));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        investorService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("Investor deleted successfully "));
    }
}
