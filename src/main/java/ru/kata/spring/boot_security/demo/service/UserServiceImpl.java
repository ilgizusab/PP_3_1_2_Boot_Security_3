package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserByID(Long Id) {
        return userRepository.findById(Id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User newUser(){
        return new User();
    }

    @Override
    public User updateUser(User updatedUser, User user) {
        if (!user.getUsername().equals(updatedUser.getUsername())) {
            user.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
            String newPassword = passwordEncoder.encode(updatedUser.getPassword());
            if (!user.getPassword().equals(newPassword)) {
                user.setPassword(newPassword);
            }
        }
        if (!user.getFirstName().equals(updatedUser.getFirstName())) {
            user.setFirstName(updatedUser.getFirstName());
        }
        if (!user.getLastName().equals(updatedUser.getLastName())) {
            user.setLastName(updatedUser.getLastName());
        }
        if (user.getAge()!=updatedUser.getAge()){
            user.setAge(updatedUser.getAge());
        }
        if (!user.getEmail().equals(updatedUser.getEmail())) {
            user.setEmail(updatedUser.getEmail());
        }
        if (user.isEnabled()!=updatedUser.isEnabled()) {
            user.setEnabled(updatedUser.isEnabled());
        }
        if (user.isAccountNonExpired()!=updatedUser.isAccountNonExpired()) {
            user.setAccountNonExpired(updatedUser.isAccountNonExpired());
        }
        if (user.isAccountNonLocked()!=updatedUser.isAccountNonLocked()) {
            user.setAccountNonLocked(updatedUser.isAccountNonLocked());
        }
        if (user.isCredentialsNonExpired()!=updatedUser.isCredentialsNonExpired()) {
            user.setCredentialsNonExpired(updatedUser.isCredentialsNonExpired());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long Id) {
        userRepository.deleteById(Id);
    }
}
