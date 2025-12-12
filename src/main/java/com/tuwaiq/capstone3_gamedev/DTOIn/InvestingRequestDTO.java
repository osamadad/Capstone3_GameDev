package com.tuwaiq.capstone3_gamedev.DTOIn;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvestingRequestDTO {
    @NotNull(message = "Offer is required")
    private Double investingOffer;
    @NotNull(message = "Share is required")
    private Double equityShare;
    @NotNull(message = "Project id is required")
    private Integer projectId;
    @NotNull(message = "Investor id is required")
    private Integer investorId;
}
