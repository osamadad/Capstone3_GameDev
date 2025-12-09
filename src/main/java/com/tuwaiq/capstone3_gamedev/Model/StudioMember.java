package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class StudioMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name cannot be empty")
    @Column(columnDefinition = "varchar(40) not null")
    private String name;

    @NotEmpty(message = "role cannot be empty")
    @Pattern(regexp = "^(leader|member)$",message = "role must be leader, member")
    @Column(columnDefinition = "varchar(20) not null")
    private String role;

    @NotNull(message = "createdAt cannot be null")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @ManyToOne
    @NotNull(message = "user cannot be null")
    private User user;

    @ManyToOne
    @JsonIgnore
    @NotNull(message = "studio cannot be null")
    private Studio studio;
}
