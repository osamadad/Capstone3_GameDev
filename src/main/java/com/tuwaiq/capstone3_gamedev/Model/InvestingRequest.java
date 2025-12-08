package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class InvestingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Offer is required")
    private String offer;

    @NotEmpty
    @Pattern(
            regexp = "(?i)^(Pending|Accepted|Rejected)$",
            message = "Status must be Pending, Accepted, or Rejected"
    )
    private String status;

    private LocalDateTime createdAt;

    @ManyToOne
    private Project project;

    @ManyToOne
    @JsonIgnore
    private Investor investor;

}
