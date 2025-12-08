package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorRepository extends JpaRepository<Investor,Integer> {

    Investor findInvestorById(Integer id);
}
