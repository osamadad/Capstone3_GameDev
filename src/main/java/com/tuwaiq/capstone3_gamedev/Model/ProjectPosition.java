package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProjectPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the available position can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the available position can't be more than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String availablePosition;
    @NotEmpty(message = "Sorry, the required skills can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the required skill can't be more than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String requiredSkills;
    @NotEmpty(message = "Sorry, the description can't be empty, please try again")
    @Size(max = 155, message = "Sorry, the description can't be more than 155 characters, please try again")
    @Column(columnDefinition = "varchar(155) not null")
    private String description;
    @NotEmpty(message = "Sorry, the compensation type can't be empty, please try again")
    @Size(max = 15, message = "Sorry, the compensation type can't be more than 15 characters, please try again")
    @Pattern(regexp = "free|revenue share|paid salary", message = "Sorry, the compensation type must be 'free', 'revenue share', or 'paid salary', please try again")
    @Column(columnDefinition = "varchar(15) not null")
    private String compensationType;
    @NotEmpty(message = "Sorry, the compensation can't be empty, please try again")
    @Size(max = 20, message = "Sorry, the compensation can't be more than 20 characters, please try again")
    @Column(columnDefinition = "varchar(20) not null")
    private String compensation;
    @NotNull(message = "Sorry, the hours per week can't be empty, please try again")
    @Max(value = 12, message = "Sorry, the hours per week can't be more than 12 hours, please try again")
    @Column(columnDefinition = "int not null")
    private Integer hoursPerWeek;
    @ManyToOne
    @JsonIgnore
    private Project project;
    @OneToMany(mappedBy = "projectPosition")
    private Set<UserRequest> userRequests;
}
