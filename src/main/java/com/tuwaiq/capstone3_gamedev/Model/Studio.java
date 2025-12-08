package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name cannot be empty")
    @Column(columnDefinition = "varchar(40) not null")
    private String name;

    @NotEmpty(message = "description cannot be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;

    @NotNull(message = "createdAt cannot be null")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "studio")
    @JsonIgnore
    private Set<StudioMember> members;

    @OneToMany(mappedBy = "studio")
    @JsonIgnore
    private Set<Project> projects;

    @OneToMany(mappedBy = "studio")
    @JsonIgnore
    private Set<Post> posts;

    @OneToMany(mappedBy = "studio")
    @JsonIgnore
    private Set<UserRequest> request;
}
