package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.ProjectInvestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectInvestorRepository extends JpaRepository<ProjectInvestor,Integer> {

    ProjectInvestor findProjectInvestorById(Integer id);
}
