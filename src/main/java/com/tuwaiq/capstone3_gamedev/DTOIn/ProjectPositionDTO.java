package com.tuwaiq.capstone3_gamedev.DTOIn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.UserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProjectPositionDTO {
    @NotEmpty(message = "Sorry, the available position can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the available position can't be more than 35 characters, please try again")
    private String availablePosition;
    @NotEmpty(message = "Sorry, the required skills can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the required skill can't be more than 35 characters, please try again")
    private String requiredSkills;
    @NotEmpty(message = "Sorry, the description can't be empty, please try again")
    @Size(max = 155, message = "Sorry, the description can't be more than 155 characters, please try again")
    private String description;
    @NotEmpty(message = "Sorry, the compensation type can't be empty, please try again")
    @Size(max = 15, message = "Sorry, the compensation type can't be more than 15 characters, please try again")
    @Pattern(regexp = "free|revenue share|paid salary", message = "Sorry, the compensation type must be 'free', 'revenue share', or 'paid salary', please try again")
    private String compensationType;
    @NotNull(message = "Sorry, the compensation can't be empty, please try again")
    @PositiveOrZero(message = "Sorry, compensation can't be negative, please try again")
    private double compensation;
    @NotNull(message = "Sorry, the hours per week can't be empty, please try again")
    @Max(value = 12, message = "Sorry, the hours per week can't be more than 12 hours, please try again")
    private Integer hoursPerWeek;
    @NotNull(message = "Sorry, the project id can't be empty, please try again")
    private Integer projectId;
}
