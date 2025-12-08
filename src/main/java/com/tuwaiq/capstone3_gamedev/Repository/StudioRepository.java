package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<Studio,Integer> {

    Studio findStudioById(Integer id);

}
