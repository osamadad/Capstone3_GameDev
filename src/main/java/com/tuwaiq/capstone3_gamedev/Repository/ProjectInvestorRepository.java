package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.ProjectInvestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectInvestorRepository extends JpaRepository<ProjectInvestor,Integer> {

    ProjectInvestor findProjectInvestorById(Integer id);

    @Query("select project.name from Project project join project.projectInvestors projectInvestor where projectInvestor.investor.id=?1")
    List<String> getInvestedProjectNamesByInvestorId(Integer investorId);
}
