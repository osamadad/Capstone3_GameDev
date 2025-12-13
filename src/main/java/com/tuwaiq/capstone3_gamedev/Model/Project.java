package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String name;

    @NotEmpty(message = "Scope cannot be empty")
    @Pattern(regexp = "^(game_jam|small|medium|big|AAA)$", message = "scope must be game_jam,small,medium,big or AAA")
    @Column(columnDefinition = "varchar(50) not null")
    private String scope;

    @NotEmpty(message = "Description cannot be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;

    @NotEmpty(message = "Engine cannot be empty")
    @Size(max = 35, message = "Sorry, the engine can't be more than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String engine;

    @NotNull(message = "Budget estimation cannot be null")
    @PositiveOrZero(message = "Budget estimation must be positive or zero")
    @Column(columnDefinition = "double not null")
    private Double budgetEstimation;

    @NotNull(message = "Earning estimation cannot be null")
    @PositiveOrZero(message = "Earning estimation must be positive or zero")
    @Column(columnDefinition = "double not null")
    private Double earningEstimation;

    @NotNull(message = "Start date cannot be null")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime endDate;

    @Column(columnDefinition = "varchar(20) not null")
    @Pattern(regexp = "^(not started|in Progress|finished)$",message = "status must be not started, in Progress or finished")
    private String status;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @ManyToOne
    private Studio studio;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<InvestingRequest> investingRequests;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Set<ProjectInvestor> projectInvestors;

    @ManyToMany(mappedBy = "projects")
    private Set<Platform> platforms;
    @ManyToMany(mappedBy = "projects")
    private Set<Genre> genres;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "project")
    @JsonIgnore
    private Set<ProjectMember> projectMembers;
    @OneToMany(orphanRemoval = true, mappedBy = "project")
    @JsonIgnore
    private Set<UserRequest> userRequests;
    @OneToMany(orphanRemoval = true, mappedBy = "project")
    @JsonIgnore
    private Set<ProjectRequest> projectRequests;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Set<ProjectPosition> projectPositions;
}