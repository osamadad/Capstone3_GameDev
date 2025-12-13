package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvestorRepository extends JpaRepository<Investor,Integer> {

    Investor findInvestorById(Integer id);

    @Query("select projectInvestor.investor from ProjectInvestor projectInvestor group by projectInvestor.investor order by count (projectInvestor.investor)")
    Investor findInvestorWithMostFundedProjects();
}
