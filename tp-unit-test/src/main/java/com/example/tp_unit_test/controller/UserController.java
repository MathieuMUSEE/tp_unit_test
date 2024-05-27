package com.example.tp_unit_test.controller;

import com.example.tp_unit_test.model.User;
import com.example.tp_unit_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(Long id){
        return userService.getUserById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public User saveUser(User user){
        return userService.saveUser(user);
    }

    @PostMapping("")
    public User createUser(Long id, User user){
        return userService.createUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(Long id){
        userService.deleteUserById(id);
    }

}



