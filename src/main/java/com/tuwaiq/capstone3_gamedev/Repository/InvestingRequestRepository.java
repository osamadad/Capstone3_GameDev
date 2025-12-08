package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.InvestingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestingRequestRepository extends JpaRepository<InvestingRequest,Integer> {

    InvestingRequest findInvestingRequestById(Integer id);
}
