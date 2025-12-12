package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Investor;
import com.tuwaiq.capstone3_gamedev.Repository.InvestorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class InvestorService {
    private final InvestorRepository investorRepository;

    public List<Investor> getAll() {
        return investorRepository.findAll();
    }

    public void add(Investor investor) {
        investor.setCreatedAt(LocalDateTime.now());
        investor.setStatus("Pending");
        investorRepository.save(investor);
    }

    public void update(Integer id, Investor investor) {
        Investor old = investorRepository.findInvestorById(id);

        if(old==null){
            throw new ApiException("Investor not found");
        }

        old.setFullName(investor.getFullName());
        old.setEmail(investor.getEmail());
        old.setPassword(investor.getPassword());
        old.setMaxAvailableBudget(investor.getMaxAvailableBudget());

        investorRepository.save(old);
    }

    public void delete(Integer id) {
        Investor investor=investorRepository.findInvestorById(id);
        if (investor==null){
            throw new ApiException("Investor not found");
        }
        investorRepository.delete(investor);
    }

}
