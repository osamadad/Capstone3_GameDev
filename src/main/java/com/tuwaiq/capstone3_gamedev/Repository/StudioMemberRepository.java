package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioMemberRepository extends JpaRepository<StudioMember,Integer> {

    StudioMember findStudioMemberById(Integer id);
}
