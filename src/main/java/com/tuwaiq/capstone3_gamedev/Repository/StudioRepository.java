package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioRepository extends JpaRepository<Studio,Integer> {

    Studio findStudioById(Integer id);
    @Query(" SELECT s FROM Studio s ORDER BY SIZE(s.projects) DESC")
    List<Studio> findStudiosOrderByProjectsSizeDesc();
}
