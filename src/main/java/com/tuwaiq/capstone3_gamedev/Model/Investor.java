package com.tuwaiq.capstone3_gamedev.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Investor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Sorry, the investor name can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the investor name can't be more than 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null")
    private String fullName;

    @Email(message = "Sorry, the investor email must be a valid email format, please try again")
    @NotEmpty(message = "Sorry, the investor email can't be empty, please try again")
    @Size(max = 35, message = "Sorry, the investor email can't be more tha 35 characters, please try again")
    @Column(columnDefinition = "varchar(35) not null unique")
    private String email;

    @NotEmpty(message = "Sorry, the investor password can't be empty, please try again")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]*$", message = "Sorry, the investor password must have 1 uppercase, 1 lowercase, 1 number, please try again")
    @Size(min = 8, max = 16, message = "Sorry, the investor password can't be less than 8 or longer than 16 characters, please try again")
    @Column(columnDefinition = "varchar(16) not null")
    private String password;

    @Pattern(regexp = "Pending|Accepted|Rejected",message = "Sorry, the investor status must be 'Pending', 'Accepted', or 'Rejected', please try again")
    @Column(columnDefinition = "varchar(10) not null")
    private String status;

    @NotNull(message = "Sorry, the investor max available budget can't be empty, please try again")
    @Positive(message = "Sorry, the investor max available budget can't be zero or less, please try again")
    @Column(columnDefinition = "double not null")
    private Double maxAvailableBudget;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "investor")
    private Set<InvestingRequest> investingRequests;

    @OneToMany(mappedBy = "investor", orphanRemoval = true)
    private Set<ProjectInvestor> projectInvestors;


}

