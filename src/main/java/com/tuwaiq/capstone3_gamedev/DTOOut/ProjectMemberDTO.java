package com.tuwaiq.capstone3_gamedev.DTOOut;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import com.tuwaiq.capstone3_gamedev.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProjectMemberDTO {

    private Integer id;
    private Integer hoursPerWeek;
    private String memberName;
    private String memberRole;
    private String projectName;
}
