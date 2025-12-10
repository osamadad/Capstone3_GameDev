package com.tuwaiq.capstone3_gamedev.DTOIn;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserRequestDTO {
    @NotEmpty(message = "Message is required")
    @Size(max = 155, message = "Sorry, the message can't be longer than 155 characters, please try again")
    @Column(columnDefinition = "varchar(155) not null")
    private String message;
    @NotNull(message = "Sorry, the user id can't be empty, please try again")
    private Integer userId;
    @NotNull(message = "Sorry, the project id can't be empty, please try again")
    private Integer projectId;
    @NotNull(message = "Sorry, the project position id can't be empty, please try again")
    private Integer projectPositionId;
}
