package com.tuwaiq.capstone3_gamedev.DTOOut;

import com.tuwaiq.capstone3_gamedev.Model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String bio;
    private String country;
    private String city;
    private Integer yearOfExperience;
    private String role;
    private String portfolioURL;
    private List<String> skills;
    private String studioName;
    private String studioRole;
    private String projectName;
}
