package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Skill;
import com.tuwaiq.capstone3_gamedev.Repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public void addSkill(Skill skill){
        skillRepository.save(skill);
    }

    public List<Skill> getSkills(){
        return skillRepository.findAll();
    }

    public void updateSkill(Integer id, Skill skill){
        Skill oldSkill=skillRepository.findSkillById(id);
        if (oldSkill==null){
            throw new ApiException("Skill not found");
        }
        oldSkill.setName(skill.getName());
        oldSkill.setUsers(null);
        skillRepository.save(oldSkill);
    }

    public void deleteSkill(Integer id){
        Skill skill=skillRepository.findSkillById(id);
        if (skill==null){
            throw new ApiException("Skill not found");
        }
        skillRepository.delete(skill);
    }
}
