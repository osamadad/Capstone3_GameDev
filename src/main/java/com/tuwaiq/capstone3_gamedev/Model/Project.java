package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @NotEmpty(message = "Scope cannot be empty medium")
    @Pattern(regexp = "^(game_jam|small|medium|big|AAA)$")
    @Column(columnDefinition = "varchar(50) not null")
    private String scope;

    @NotEmpty(message = "Description cannot be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;

    @NotEmpty(message = "Genre cannot be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String genre;

    @NotNull(message = "Budget estimation cannot be null")
    @PositiveOrZero(message = "Budget estimation must be positive or zero")
    @Column(columnDefinition = "double not null")
    private Double budget_estimation;

    @NotNull(message = "Earning estimation cannot be null")
    @PositiveOrZero(message = "Earning estimation must be positive or zero")
    @Column(columnDefinition = "double not null")
    private Double earning_estimation;

    @NotNull(message = "Start date cannot be null")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime start_date;

    @NotNull(message = "End date cannot be null")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime end_date;

    @NotEmpty(message = "Status cannot be empty")
    @Column(columnDefinition = "varchar(20) not null inProgress")
    @Pattern(regexp = "^(inProgress|finished)$")
    private String status;

    @NotNull(message = "Created at cannot be null")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime created_at;

    @ManyToOne
    @JsonIgnore
    private Studio studio;
}