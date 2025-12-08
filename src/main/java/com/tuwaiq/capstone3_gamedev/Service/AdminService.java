package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Admin;
import com.tuwaiq.capstone3_gamedev.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public void addAdmin(Admin admin){
        admin.setCreated_at(LocalDateTime.now());
        adminRepository.save(admin);
    }

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public void updateAdmin(Integer id, Admin admin){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if (oldAdmin == null){
            throw new ApiException("Admin not found");
        }
        oldAdmin.setUsername(admin.getUsername());
        oldAdmin.setPassword(admin.getPassword());
        oldAdmin.setEmail(admin.getEmail());
        adminRepository.save(oldAdmin);
    }

    public void deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);
        if (admin == null){
            throw new ApiException("Admin not found");
        }
        adminRepository.delete(admin);
    }
}
