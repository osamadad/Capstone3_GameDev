package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Project findProjectById(Integer id);
    List<Project> findProjectsByEarningEstimationGreaterThan(Double earningEstimation);
    List<Project> findProjectsByBudgetEstimationGreaterThan(Double budgetEstimation);
    List<Project> findProjectsByScope(String scope);
    List<Project> findProjectsByStudio_Id(Integer studioId);
    List<Project> findProjectsByStatus(String status);
    @Query("SELECT p FROM Project p WHERE YEAR(p.endDate) = YEAR(CURRENT_DATE)")
    List<Project> findThisYearProjects();

}
