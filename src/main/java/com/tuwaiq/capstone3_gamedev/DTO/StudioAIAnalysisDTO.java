package com.tuwaiq.capstone3_gamedev.DTO;

import lombok.Data;

import java.util.List;

@Data
public class StudioAIAnalysisDTO {

    private String performanceLevel;
    private String investmentReadiness;
    private String riskLevel;
    private Double fundingSuccessRate;
    private Double teamStrengthScore;
    private String growthPotential;
    private List<String> keyStrengths;
    private List<String> keyWeaknesses;
    private String aiSummary;
    private List<String> recommendations;
}

