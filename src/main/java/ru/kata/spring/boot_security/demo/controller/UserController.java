package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String findAll(@AuthenticationPrincipal User activeUser, Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", activeUser.getRoles());
        model.addAttribute("user", activeUser);
        return "admin";
    }

    @PostMapping("/admin/create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("role") ArrayList<String> role) {
        userService.saveUser(user, role);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUserForm(@RequestParam("role") ArrayList<String> role, @ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.saveUser(user, role);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String printWelcome(@AuthenticationPrincipal User activeUser, Model model) {
        model.addAttribute("roles", activeUser.getRoles());
        model.addAttribute("user", activeUser);
        return "user";
    }
}
