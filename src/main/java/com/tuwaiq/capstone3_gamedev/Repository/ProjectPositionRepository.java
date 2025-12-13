package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.ProjectPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPositionRepository extends JpaRepository<ProjectPosition,Integer> {

    ProjectPosition findProjectPositionById(Integer id);

    List<ProjectPosition> findProjectPositionByProject_Id(Integer projectId);
}
