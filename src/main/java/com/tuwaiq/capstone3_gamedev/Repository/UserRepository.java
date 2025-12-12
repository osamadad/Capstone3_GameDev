package com.tuwaiq.capstone3_gamedev.Repository;

import com.tuwaiq.capstone3_gamedev.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    @Query("""
        select distinct u
        from User u
        join u.skills s
        where lower(s.name) = lower(:skill)
    """)
    List<User> findUsersBySkillName(@Param("skill") String skill);

    List<User> findByCityIgnoreCase(String city);

    List<User> findByCountryIgnoreCase(String country);

    List<User> findByYearOfExperienceGreaterThan(Integer yearOfExperience);

    List<User> findByRoleIgnoreCase(String role);


}
