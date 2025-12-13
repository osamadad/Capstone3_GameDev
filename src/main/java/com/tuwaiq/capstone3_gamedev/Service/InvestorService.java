package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOOut.InvestorDTO;
import com.tuwaiq.capstone3_gamedev.Model.Investor;
import com.tuwaiq.capstone3_gamedev.Repository.InvestorRepository;
import com.tuwaiq.capstone3_gamedev.Repository.ProjectInvestorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class InvestorService {

    private final InvestorRepository investorRepository;
    private final ProjectInvestorRepository projectInvestorRepository;

    public List<Investor> getInvestors() {
        return investorRepository.findAll();
    }

    public void addInvestor(Investor investor) {
        investor.setCreatedAt(LocalDateTime.now());
        investor.setStatus("Pending");
        investorRepository.save(investor);

        sendWelcomeEmail(investor.getEmail(),investor.getFullName());
    }

    public void updateInvestor(Integer id, Investor investor) {
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

    public void deleteInvestor(Integer id) {
        Investor investor=investorRepository.findInvestorById(id);
        if (investor==null){
            throw new ApiException("Investor not found");
        }
        investorRepository.delete(investor);
    }

    private void sendWelcomeEmail(String investorEmail, String fullName) {
        String webhookUrl = "http://localhost:5678/webhook/investor-welcome-email";

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("investorEmail", investorEmail);
        payload.put("fullName", fullName);

        new RestTemplate().postForObject(
                webhookUrl,
                payload,
                String.class
        );

    }

    public InvestorDTO getInvestorWithMostFundedProject(){
        Investor investor=investorRepository.findInvestorWithMostFundedProjects();

        if (investor==null){
            throw new ApiException("Investor not found");
        }

        List<String> fundedProjects=projectInvestorRepository.getInvestedProjectNamesByInvestorId(investor.getId());
        return new InvestorDTO(investor.getId(), investor.getFullName(), investor.getStatus(), investor.getMaxAvailableBudget(), fundedProjects);
    }
}
