package com.tuwaiq.capstone3_gamedev.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Investor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Full name is required")
    private String fullName;

    @Email
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotNull
    @Positive
    private Double availableBudget;

    @NotNull
    @Min(0)
    private Integer fundedProjects;

    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "investor")
    private Set<InvestingRequest> investingRequests;
}

