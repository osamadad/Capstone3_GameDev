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
    private String CompensationType;
    @Column(columnDefinition = "double not null")
    private Double CompensationAmount;
    @Column(columnDefinition = "double not null")
    private Double revenueSharePercentage;
    @Column(columnDefinition = "int not null")
    private Integer hoursPerWeek;
    private LocalDateTime created_at;
    @ManyToOne
    @JsonIgnore
    private User user;
    @ManyToOne
    private Project project;
}
