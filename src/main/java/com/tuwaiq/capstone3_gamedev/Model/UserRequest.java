package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Message is required")
    private String message;

    @NotEmpty(message = "Status is required")
    @Pattern(
            regexp = "(?i)^(Pending|Accepted|Rejected)$",
            message = "Status must be PENDING, ACCEPTED, or REJECTED"
    )
    private String status;

    private LocalDateTime createdAt;


    @ManyToOne
    @JsonIgnore
    private User user;


    @ManyToOne
    @JsonIgnore
    private Studio studio;



}
