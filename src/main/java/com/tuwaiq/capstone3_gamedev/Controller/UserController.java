package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.User;
import com.tuwaiq.capstone3_gamedev.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user){
        userService.updateUser(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }

    @PutMapping("/assign-skill/{userId}/{skillId}")
    public ResponseEntity<?> assignSkill(@PathVariable Integer userId, @PathVariable Integer skillId){
        userService.assignSkill(userId,skillId);
        return ResponseEntity.status(200).body(new ApiResponse("Skill assign to user successfully"));
    }

//    @GetMapping("/skill/{skillName}")
//    public ResponseEntity<?> getUsersBySkill(@PathVariable String skillName) {
//        return ResponseEntity.status(200).body(userService.getUsersBySkill(skillName));
//    }

    @GetMapping("/city/{city}")
    public ResponseEntity<?> getUsersByCity(@PathVariable String city) {
        return ResponseEntity.status(200).body(userService.getUsersByCity(city));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<?> getUsersByCountry(@PathVariable String country) {
        return ResponseEntity.status(200).body(userService.getUsersByCountry(country));
    }

    @GetMapping("/experience-more-than/{years}")
    public ResponseEntity<?> getUsersWithExperienceMoreThan(@PathVariable Integer years) {
        return ResponseEntity.status(200).body(userService.getUsersWithExperienceHigherThan(years));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.status(200).body(userService.getUsersByRole(role));
    }

}
