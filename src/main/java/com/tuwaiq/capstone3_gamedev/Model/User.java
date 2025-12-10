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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the username can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the username can't be more than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String username;
    @NotEmpty(message = "Sorry, the user password can't be empty, please try again")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*$", message = "Sorry, the user password must have 1 uppercase, 1 lowercase, 1 number, please try again")
    @Size(min = 8, max = 16, message = "Sorry, the user password can't be less than 8 or longer than 16 characters, please try again")
    @Column(columnDefinition = "varchar(16) not null")
    private String password;
    @Email(message = "Sorry, the user email must follow a valid email format, please try again")
    @Size(max = 35, message = "Sorry, the user email can't be more than 35 character, please try again")
    @Column(columnDefinition = "varchar(35) not null unique")
    private String email;
    @Size(max = 255, message = "Sorry, the user bio can't be more than 255 character, please try again")
    @Column(columnDefinition = "varchar(255)")
    private String bio;
    @Size(max = 50, message = "Sorry, the user country can't be more than 50 character, please try again")
    @Column(columnDefinition = "varchar(50)")
    private String country;
    @Size(max = 50, message = "Sorry, the user city can't be more than 50 character, please try again")
    @Column(columnDefinition = "varchar(50)")
    private String city;
    @NotNull(message = "Sorry, the user years of experience can't be empty, please try again")
    @PositiveOrZero(message = "Sorry, the user years of experience can't be negative, please try again")
    @Column(columnDefinition = "int not null")
    private Integer yearOfExperience;
    @NotEmpty(message = "Sorry, the user role can't be empty, please try again")
    @Size( max = 30, message = "Sorry, the user role can't be more than 50 character, please try again")
    @Pattern(regexp = "(?i)^(game design|programmer|artist|producer|sound designer)$", message = "Sorry, the user role can only be 'game designer', 'programmer', 'artist', 'producer', or 'sound designer', please try again")
    @Column(columnDefinition = "varchar(30) not null")
    private String role;
    @NotEmpty(message = "Sorry, the user portfolio can't be empty, please try again")
    @Size(max = 155, message = "Sorry, the user portfolio can't be more than 155 character, please try again")
    @Column(columnDefinition = "varchar(155) not null")
    private String portfolioURL;
    @Column(columnDefinition = "datetime")
    private LocalDateTime created_at;
    @ManyToMany
    private Set<Skill> skills;
    @OneToMany(orphanRemoval = true, mappedBy = "user")
    private Set<UserRequest> userRequests;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private StudioMember studioMember;
    @OneToMany(orphanRemoval = true, mappedBy = "user")
    private Set<ProjectMember> projectMember;
}
