package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioMemberRepository extends JpaRepository<StudioMember,Integer> {

    StudioMember findStudioMemberById(Integer id);
    boolean existsByUserIdAndStudioId(Integer userId, Integer studioId);
}
