package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Admin findAdminById(Integer id);
}
