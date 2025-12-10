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
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudioMember {
    @Id
    private Integer id;
    @Column(columnDefinition = "varchar(20) not null")
    private String role;
    private LocalDateTime createdAt;
    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;
    @ManyToOne
    @JsonIgnore
    private Studio studio;
}
