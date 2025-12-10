package com.tuwaiq.capstone3_gamedev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;
    @NotEmpty(message = "Sorry, the post type can't be empty, please try again")
    @Size(max = 20, message = "Sorry, the post type can't be more than 20 characters, please try again")
    @Pattern(regexp = "hiring|announcement|showcase|investor pitch" , message = "Sorry, the post type must be 'hiring', 'announcement', 'showcase', or 'investor pitch', please try again")
    @Column(columnDefinition = "varchar(20) not null")
    private String type;

    @Column(columnDefinition = "varchar(255)")
    private String media_url;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime created_at;

    @ManyToOne
    @JsonIgnore
    private Studio studio;

}