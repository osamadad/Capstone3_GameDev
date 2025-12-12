package com.tuwaiq.capstone3_gamedev.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProjectSuccessPredictionDTO {

    private Integer projectId;
    private Double successProbability;
    private String riskLevel;
    private String marketPotential;
    private String teamReadiness;
    private List<String> keySuccessFactors;
    private List<String> mainRisks;
    private String aiSummary;
    private List<String> recommendations;
}

