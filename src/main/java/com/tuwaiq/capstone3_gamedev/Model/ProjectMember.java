package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the compensation type can't be empty, please try again")
    @Size(max = 20, message = "Sorry, the compensation type can't be more than 20 characters, please try again")
    @Pattern(regexp = "free|revenue share|paid salary", message = "Sorry, the compensation type must be 'free', 'revenue share', or 'paid salary', please try again")
    @Column(columnDefinition = "varchar(20) not null")
    private String CompensationType;
    @NotNull(message = "Sorry, the compensation amount can't be empty, please try again")
    @PositiveOrZero(message = "Sorry, the compensation amount can't negative, please try again")
    @Column(columnDefinition = "int not null")
    private Integer CompensationAmount;
    @NotNull(message = "Sorry, the revenue share percentage can't be empty, please try again")
    @PositiveOrZero(message = "Sorry, the revenue share percentage can't negative, please try again")
    @Column(columnDefinition = "double not null")
    private Double revenueSharePercentage;
    @NotNull(message = "Sorry, the hours per week can't be empty, please try again")
    @PositiveOrZero(message = "Sorry, the hours per week can't negative, please try again")
    @Column(columnDefinition = "int not null")
    private Integer hoursPerWeek;
    private LocalDateTime created_at;
    @ManyToOne
    @JsonIgnore
    private StudioMember studioMember;
    @ManyToOne
    private Project project;
}
