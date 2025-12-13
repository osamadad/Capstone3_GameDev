package com.tuwaiq.capstone3_gamedev.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuwaiq.capstone3_gamedev.Ai.Message;
import com.tuwaiq.capstone3_gamedev.Ai.OpenAIRequest;
import com.tuwaiq.capstone3_gamedev.Ai.OpenAIResponse;
import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTO.ProjectSuccessPredictionDTO;
import com.tuwaiq.capstone3_gamedev.DTO.StudioAIAnalysisDTO;
import com.tuwaiq.capstone3_gamedev.DTO.StudioSuggestionDTO;
import com.tuwaiq.capstone3_gamedev.Model.Genre;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.Studio;
import com.tuwaiq.capstone3_gamedev.Model.User;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioRepository;
import com.tuwaiq.capstone3_gamedev.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor

public class OpenAIService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}")
    private String apiKey;

    private final StudioRepository studioRepository;
    private final StudioMemberRepository studioMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public StudioAIAnalysisDTO analyzeStudio(Integer studioId) {
        Studio studio = studioRepository.findById(studioId).orElse(null);
        if (studio == null) {
            throw new ApiException("Studio not found");
        }

        int totalMembers = studioMemberRepository.findAllByStudioId(studioId).size();
        List<Project> projects = projectRepository.findProjectsByStudio_Id(studioId);

        int totalProjects = projects.size();
        int fundedProjects = (int) projects.stream().filter(p -> p.getProjectInvestors() != null && !p.getProjectInvestors().isEmpty()).count();


        double fundingRate = totalProjects == 0 ? 0 : (fundedProjects * 100.0) / totalProjects;


        String prompt = """
        You are an AI business analyst.

        Analyze this game studio and return ONLY a valid JSON response in this exact format:

        {
          "performanceLevel": "",
          "investmentReadiness": "",
          "riskLevel": "",
          "fundingSuccessRate": 0.0,
          "teamStrengthScore": 0.0,
          "growthPotential": "",
          "keyStrengths": [],
          "keyWeaknesses": [],
          "aiSummary": "",
          "recommendations": []
        }

        Studio Data:
        - Total Members: %d
        - Total Projects: %d
        - Funded Projects: %d
        - Funding Success Rate: %.2f%%
        """.formatted(
                totalMembers,
                totalProjects,
                fundedProjects,
                fundingRate
        );


        OpenAIRequest request = new OpenAIRequest("gpt-4o-mini", List.of(new Message("user", prompt)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OpenAIResponse> response =
                restTemplate.postForEntity(
                        "https://api.openai.com/v1/chat/completions",
                        entity,
                        OpenAIResponse.class
                );

        String aiJson = response.getBody().getChoices().get(0).getMessage().getContent();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(aiJson, StudioAIAnalysisDTO.class);
        } catch (Exception e) {
            throw new ApiException("Failed to parse AI response");
        }


    }

    public StudioSuggestionDTO suggestStudiosForUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        List<Studio> studios = studioRepository.findAll();

        String prompt = """
     You are an AI career advisor for game developers.

    Suggest the best studios for this user and return ONLY valid JSON in this exact format:

    {
      "userId": %d,
      "suggestedStudios": [
        {
          "studioId": 0,
          "studioName": "",
          "matchScore": 0,
          "reason": ""
        }
      ],
      "aiSummary": ""
    }

    User Data:
    - Role: %s
    - Years of Experience: %d
    - Skills: %s

    Available Studios:
    %s
    """.formatted(
                user.getId(),
                user.getRole(),
                user.getYearOfExperience(),
                user.getSkills().stream().map(s -> s.getName()).toList(),
                studios.stream().map(s -> "Studio ID: " + s.getId() + ", Name: " + s.getName()).toList()
        );

        OpenAIRequest request = new OpenAIRequest(
                "gpt-4o-mini",
                List.of(new Message("user", prompt))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OpenAIResponse> response =
                restTemplate.postForEntity(
                        "https://api.openai.com/v1/chat/completions",
                        entity,
                        OpenAIResponse.class
                );

        String aiJson = response.getBody().getChoices().get(0).getMessage().getContent();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(aiJson, StudioSuggestionDTO.class);
        } catch (Exception e) {
            throw new ApiException("Failed to parse AI studio suggestion");
        }

    }

    public ProjectSuccessPredictionDTO predictProjectSuccess(Integer projectId) {

        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            throw new ApiException("Project not found");
        }

        Studio studio = project.getStudio();

        int teamSize = studioMemberRepository.findAllByStudioId(studio.getId()).size();

        boolean isFunded = project.getProjectInvestors() != null && !project.getProjectInvestors().isEmpty();

        String prompt = """
    You are an AI business and game market analyst.

    Predict the success of the following game project and return ONLY valid JSON in this exact format:

    {
      "projectId": %d,
      "successProbability": 0.0,
      "riskLevel": "",
      "marketPotential": "",
      "teamReadiness": "",
      "keySuccessFactors": [],
      "mainRisks": [],
      "aiSummary": "",
      "recommendations": []
    }

    Project Data:
    - Project Name: %s
    - Genre: %s
    - Is Funded: %s

    Studio Data:
    - Studio Name: %s
    - Team Size: %d
    """.formatted(
                project.getId(),
                project.getName(),
                project.getGenres().stream().map(Genre::getName).toList(),
                isFunded ? "YES" : "NO",
                studio.getName(),
                teamSize
        );

        OpenAIRequest request = new OpenAIRequest(
                "gpt-4o-mini",
                List.of(new Message("user", prompt))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<OpenAIResponse> response =
                restTemplate.postForEntity(
                        "https://api.openai.com/v1/chat/completions",
                        entity,
                        OpenAIResponse.class
                );

        String aiJson = response.getBody().getChoices().get(0).getMessage().getContent();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(aiJson, ProjectSuccessPredictionDTO.class);
        } catch (Exception e) {
            throw new ApiException("Failed to parse AI project success prediction");
        }
    }


}

