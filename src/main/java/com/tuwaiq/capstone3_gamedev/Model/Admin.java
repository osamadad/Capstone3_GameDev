package com.tuwaiq.capstone3_gamedev.Model;

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
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the admin username can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the admin username can't be more than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String username;
    @NotEmpty(message = "Sorry, the admin password can't be empty, please try again")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*$", message = "Sorry, the admin password must have 1 uppercase, 1 lowercase, 1 number, please try again")
    @Size(min = 8, max = 16, message = "Sorry, the admin password can't be less than 8 or longer than 16 characters, please try again")
    @Column(columnDefinition = "varchar(16) not null")
    private String password;
    @Email(message = "Sorry, the admin email must follow a valid email format, please try again")
    @Size(max = 35, message = "Sorry, the admin email can't be more than 35 character, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String email;
    @Column(columnDefinition = "datetime")
    private LocalDateTime createdAt;
}
