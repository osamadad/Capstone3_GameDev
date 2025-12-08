package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);
}
