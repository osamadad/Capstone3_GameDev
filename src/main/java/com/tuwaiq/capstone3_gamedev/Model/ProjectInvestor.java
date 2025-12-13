package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class ProjectInvestor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "double not null")
    private Double investedAmount;
    @Column(columnDefinition = "double not null")
    private Double equityShare;
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
    @ManyToOne
    @JsonIgnore
    private Project project;
    @ManyToOne
    @JsonIgnore
    private Investor investor;
}
