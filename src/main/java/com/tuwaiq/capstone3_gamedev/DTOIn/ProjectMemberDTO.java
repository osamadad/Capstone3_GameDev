package com.tuwaiq.capstone3_gamedev.DTOIn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProjectMemberDTO {
    @NotEmpty(message = "Sorry, the compensation type can't be empty, please try again")
    @Size(max = 15, message = "Sorry, the compensation type can't be more than 15 characters, please try again")
    @Pattern(regexp = "free|revenue share|paid salary", message = "Sorry, the compensation type must be 'free', 'revenue share', or 'paid salary', please try again")
    private String CompensationType;
    @NotNull(message = "Sorry, the compensation can't be empty, please try again")
    @PositiveOrZero(message = "Sorry, compensation can't be negative, please try again")
    private double compensation;
    @NotNull(message = "Sorry, the hours per week can't be empty, please try again")
    private Integer hoursPerWeek;
    @NotNull(message = "Sorry, the user id can't be empty, please try again")
    private Integer userId;
    @NotNull(message = "Sorry, the user id can't be empty, please try again")
    private Integer projectId;
}
