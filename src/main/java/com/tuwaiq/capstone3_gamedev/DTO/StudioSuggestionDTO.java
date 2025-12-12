package com.tuwaiq.capstone3_gamedev.DTO;

import lombok.Data;

import java.util.List;

@Data
public class StudioSuggestionDTO {

    private Integer userId;
    private List<SuggestedStudio> suggestedStudios;
    private String aiSummary;

    @Data
    public static class SuggestedStudio {
        private Integer studioId;
        private String studioName;
        private Integer matchScore;
        private String reason;
    }
}

