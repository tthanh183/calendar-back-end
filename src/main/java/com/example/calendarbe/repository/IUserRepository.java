package com.example.calendarbe.repository;

import com.example.calendarbe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserByEmailAndPassword(String email, String password);
}
