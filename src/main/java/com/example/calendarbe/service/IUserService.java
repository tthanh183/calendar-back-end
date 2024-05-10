package com.example.calendarbe.service;

import com.example.calendarbe.model.User;

import java.util.Optional;

public interface IUserService {
    User findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserById(Long id);

    void save(User user);
}
