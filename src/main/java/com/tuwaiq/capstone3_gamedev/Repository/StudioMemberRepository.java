package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioMemberRepository extends JpaRepository<StudioMember,Integer> {

    StudioMember findStudioMemberById(Integer id);

    boolean existsByUserIdAndStudioId(Integer userId, Integer studioId);

    List<StudioMember> findAllByStudioId(Integer studioId);

    boolean existsStudioMemberById(Integer id);

    boolean existsByUser_Id(Integer userId);

    @Query("select studioMember from StudioMember studioMember where studioMember.studio.id=?1 and studioMember.role='leader'")
    StudioMember getLeaderOfStudioByStudioId(Integer studioId);
}
