package ru.kata.spring.boot_security.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        log.info("UserService. Get user by id : {} ", id);
        return user;
    }

    public List<User> findAll() {
        List<User> allUsers = userRepository.findAll();
        log.info("Get all users");
        return allUsers;
    }

    public User saveUser(User user, ArrayList<String> role) {
        Set<Role> roleSet = new HashSet<>();
        for (String indexRole: role) {
            roleSet.add(roleService.findById(Long.valueOf(indexRole)));
        }
        user.setRoles(roleSet);
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
