package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRequestRepository extends JpaRepository<UserRequest,Integer> {

    UserRequest findUserRequestById(Integer id);

    List<UserRequest> findAllByUserId(Integer userId);

    List<UserRequest> findAllByProjectId(Integer studioId);
}
