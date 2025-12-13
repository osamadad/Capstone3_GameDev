package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Integer> {

    Skill findSkillById(Integer id);

    @Query("select skill.name from Skill skill join skill.users user where user.id=?1")
    List<String> getSkillsNameByUserId(Integer userId);
}
