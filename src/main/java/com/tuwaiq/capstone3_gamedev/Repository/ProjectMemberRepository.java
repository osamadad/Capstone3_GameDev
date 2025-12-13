package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Integer> {

    ProjectMember findProjectMemberById(Integer id);

    List<ProjectMember> findProjectMemberByProject_Id(Integer projectId);
}
