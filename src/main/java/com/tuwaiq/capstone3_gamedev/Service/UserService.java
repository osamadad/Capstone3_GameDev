package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.DTOOut.UserDTO;
import com.tuwaiq.capstone3_gamedev.Model.Skill;
import com.tuwaiq.capstone3_gamedev.Model.User;
import com.tuwaiq.capstone3_gamedev.Repository.SkillRepository;
import com.tuwaiq.capstone3_gamedev.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public void addUser(User user){
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        sendAiWelcomeEmail(user.getEmail(),user.getUsername());
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void updateUser(Integer id, User user){
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null){
            throw new ApiException("User not found");
        }
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setBio(user.getBio());
        oldUser.setCountry(user.getCountry());
        oldUser.setCity(user.getCity());
        oldUser.setYearOfExperience(user.getYearOfExperience());
        oldUser.setPortfolioURL(user.getPortfolioURL());
        userRepository.save(oldUser);
    }

    // system endpoint
    public void assignSkill(Integer userId, Integer skillId){
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        Skill skill=skillRepository.findSkillById(skillId);
        if (skill==null){
            throw new ApiException("Skill not found");
        }
        user.getSkills().add(skill);
        skill.getUsers().add(user);
        userRepository.save(user);
        skillRepository.save(skill);
    }

    public void deleteUser(Integer id){
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }

    //Endpoints
    private List<UserDTO> convertUserToUserDTO(List<User> users) {
        List<UserDTO> userDTOS=new ArrayList<>();
        for (User user: users){
            List<String> skillsNames=skillRepository.getSkillsNameByUserId(user.getId());
            UserDTO userDTO=new UserDTO(user.getId(),user.getUsername(),user.getBio(),user.getCountry(),user.getCity(),user.getYearOfExperience(),user.getRole(),user.getPortfolioURL(),skillsNames,user.getStudioMember().getStudio().getName(),user.getStudioMember().getRole());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public List<UserDTO> getUsersBySkill(String skill) {
        List<User> users = userRepository.findUsersBySkillName(skill);

        if (users.isEmpty()) {
            throw new ApiException("No users found with skill: " + skill);
        }

        return convertUserToUserDTO(users);
    }

    public List<UserDTO> getUsersByCity(String city) {
        List<User> users = userRepository.findByCityIgnoreCase(city);

        if (users.isEmpty()) {
            throw new ApiException("No users found in city: " + city);
        }
        return convertUserToUserDTO(users);
    }

    public List<UserDTO> getUsersByCountry(String country) {
        List<User> users = userRepository.findByCountryIgnoreCase(country);

        if (users.isEmpty()) {
            throw new ApiException("No users found in country: " + country);
        }

        return convertUserToUserDTO(users);
    }

    public List<UserDTO> getUsersWithExperienceHigherThan(Integer years) {
        List<User> users = userRepository.findByYearOfExperienceGreaterThan(years);

        if (users.isEmpty()) {
            throw new ApiException("No users found with more than " + years + " years of experience");
        }

        return convertUserToUserDTO(users);
    }

    public List<UserDTO> getUsersByRole(String role) {
        List<User> users = userRepository.findByRoleIgnoreCase(role);

        if (users.isEmpty()) {
            throw new ApiException("No users found with role: " + role);
        }

        return convertUserToUserDTO(users);
    }
    private void sendAiWelcomeEmail(String userEmail, String username) {
        String webhookUrl = "http://localhost:5678/webhook/generate-welcome-email";

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("email", userEmail);
        payload.put("username", username);

        new RestTemplate().postForObject(
                webhookUrl,
                payload,
                String.class
        );

    }

}
