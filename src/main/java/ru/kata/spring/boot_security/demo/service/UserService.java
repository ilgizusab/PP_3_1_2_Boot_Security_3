package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByID(Long Id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    List<Role> getAllRoles();
    User newUser();
    User addUser(User user);
    User updateUser(User updatedUser, User user);
    void deleteUser(Long Id);
}
