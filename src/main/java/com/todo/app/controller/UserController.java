package com.todo.app.controller;

import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.UserDto;
import com.todo.app.model.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // @GetMapping("/user/{id}")
    // public ResponseEntity<?> getUser(@PathVariable Long id) {
    //     return ResponseEntity.ok(userService.getUser(id));
    // }

    // @PostMapping("/user")
    // public ResponseEntity<?> createUser(@RequestBody UserEntity entity) {
    //     return ResponseEntity.ok(userService.createUser(entity));
    // }
    
    
    @GetMapping("/users/register")
    public String createUser() {
        return "register";
    }

    
    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute UserEntity userEntity) {
        userService.createUser(userEntity);
        return "redirect:/users/login";
    }

    @GetMapping("/users/login")
    public String getMethodName() {
        return "login";
    }
    
    
    @PostMapping("/users/login")
    public String postMethodName(@RequestParam("username") String username, HttpSession session) {
        UserDto userDto = userService.getUserByUsername(username);
        session.setAttribute("userDto", userDto);

        return "redirect:/tasks";
    }
    

}
