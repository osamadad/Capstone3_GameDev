package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the platform name can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the platform name can't be more than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String name;
    @ManyToMany
    @JsonIgnore
    private Set<Project> projects;
}
