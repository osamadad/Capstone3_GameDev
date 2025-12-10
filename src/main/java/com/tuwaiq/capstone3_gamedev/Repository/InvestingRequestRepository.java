package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.InvestingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestingRequestRepository extends JpaRepository<InvestingRequest,Integer> {

    InvestingRequest findInvestingRequestById(Integer id);

    boolean existsByInvestorIdAndProjectId(Integer investorId, Integer projectId);

    List<InvestingRequest> findAllByInvestorId(Integer investorId);

    List<InvestingRequest> findAllByProjectId(Integer projectId);


}
