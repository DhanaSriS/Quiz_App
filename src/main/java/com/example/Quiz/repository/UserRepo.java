package com.example.Quiz.repository;

import com.example.Quiz.model.User;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
