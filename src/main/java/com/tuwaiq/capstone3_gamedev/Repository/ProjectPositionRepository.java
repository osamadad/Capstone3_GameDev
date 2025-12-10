package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.ProjectPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPositionRepository extends JpaRepository<ProjectPosition,Integer> {

    ProjectPosition findProjectPositionById(Integer id);
}
