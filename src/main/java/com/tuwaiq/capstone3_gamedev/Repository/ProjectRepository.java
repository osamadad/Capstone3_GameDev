package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.Genre;
import com.tuwaiq.capstone3_gamedev.Model.Platform;
import com.tuwaiq.capstone3_gamedev.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Project findProjectById(Integer id);

//    List<Project> findAllByStudioIdAndInvestorIsNotNull(Integer studioId);

//    List<Project> findAllByInvestorId(Integer investorId);


    List<Project> findProjectsByEarningEstimationGreaterThan(Double earningEstimation);
    List<Project> findProjectsByBudgetEstimationGreaterThan(Double budgetEstimation);
    List<Project> findProjectsByScope(String scope);
    List<Project> findProjectsByStudio_Id(Integer studioId);
    List<Project> findProjectsByStatus(String status);
    @Query("SELECT p FROM Project p WHERE YEAR(p.endDate) = YEAR(CURRENT_DATE)")
    List<Project> findThisYearProjects();

    List<Project> findProjectByEngine(String engine);

    @Query("select project from Project project join project.genres genre where genre.name=?1")
    List<Project> findProjectByGenre(String name);

    @Query("select project from Project project join project.platforms platform where platform.name=?1")
    List<Project> findProjectByPlatforms(String name);
}
