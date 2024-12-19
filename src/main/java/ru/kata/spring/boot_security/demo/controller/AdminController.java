package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String showAdminPanel(Model model, Authentication auth) {
        List<User> users = userService.getAllUsers();
        User user = userService.getUserByUsername(auth.getName());
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin";
    }

    @GetMapping("/admin/new")
    public String showNewUserForm(Model model, HttpSession session) {
        User user = userService.newUser();
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("user", user);
        session.setAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "newUser";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = userService.getUserByID(id).orElse(userService.newUser());
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("user", user);
        session.setAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "editUser";
    }

    @PostMapping("admin/edit/{id}")
    public String updateUser(@ModelAttribute("user") User updatedUser, @SessionAttribute("user")User user) {
        userService.updateUser(updatedUser, user);
        return "redirect:/admin?success";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin?deleted";
    }
}
