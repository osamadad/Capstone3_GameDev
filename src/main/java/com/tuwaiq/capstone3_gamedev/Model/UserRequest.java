package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(155) not null")
    private String message;
    @Column(columnDefinition = "varchar(10) not null ")
    @Pattern(regexp = "Pending|Rejected|Accepted", message = "Status must be 'Pending', 'Rejected', or 'Accepted'")         /* as a refrence */
    private String status;
    @Column(columnDefinition = "datetime")
    private LocalDateTime createdAt;
    @ManyToOne
    private User user;
    @ManyToOne
    private Project project;
    @ManyToOne
    private ProjectPosition projectPosition;



}
