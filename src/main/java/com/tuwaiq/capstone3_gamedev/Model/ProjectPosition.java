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
public class ProjectPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(35) not null")
    private String availablePosition;
    @Column(columnDefinition = "varchar(35) not null")
    private String requiredSkills;
    @Column(columnDefinition = "varchar(155) not null")
    private String description;
    @Column(columnDefinition = "varchar(15) not null")
    private String compensationType;
    @Column(columnDefinition = "double not null")
    private double compensation;
    @Column(columnDefinition = "int not null")
    private Integer hoursPerWeek;
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
    @ManyToOne
    @JsonIgnore
    private Project project;
    @OneToMany(orphanRemoval = true, mappedBy = "projectPosition")
    private Set<UserRequest> userRequests;
    @OneToMany(orphanRemoval = true, mappedBy = "projectPosition")
    private Set<ProjectRequest> projectRequests;
}
