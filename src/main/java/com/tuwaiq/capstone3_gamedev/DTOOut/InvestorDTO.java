package com.tuwaiq.capstone3_gamedev.DTOOut;

import com.tuwaiq.capstone3_gamedev.Model.InvestingRequest;
import com.tuwaiq.capstone3_gamedev.Model.ProjectInvestor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class InvestorDTO {

    private Integer id;
    private String fullName;
    private String status;
    private Double maxAvailableBudget;
    private Set<String> projectInvestedIn;
}
