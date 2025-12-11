package com.tuwaiq.capstone3_gamedev.DTOIn;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectRequestDTO {
    @NotEmpty(message = "Message is required")
    @Size(max = 155, message = "Sorry, the message can't be longer than 155 characters, please try again")
    private String message;
    @NotNull(message = "Sorry, the user id can't be empty, please try again")
    private Integer userId;
    @NotNull(message = "Sorry, the project id can't be empty, please try again")
    private Integer projectId;
    @NotNull(message = "Sorry, the project position id can't be empty, please try again")
    private Integer projectPositionId;
}
