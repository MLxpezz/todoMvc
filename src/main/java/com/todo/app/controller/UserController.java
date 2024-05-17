package com.todo.app.controller;

import com.todo.app.model.entities.dto.LoginRequest;
import com.todo.app.model.entities.dto.UserDto;
import com.todo.app.model.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/register")
    public String createUser(Model model) {
        model.addAttribute("loginRequest", new LoginRequest(null, null));
        return "register";
    }

    
    @PostMapping("/users/register")
    public String registerUser(@Valid @ModelAttribute LoginRequest loginRequest, BindingResult result, Model model) {

        UserDto userDto = null;

        try {
            userDto = userService.getUserByUsername(loginRequest.username());

            if(loginRequest.username() != null && userDto.getUsername() != null && loginRequest.username().equals(userDto.getUsername())) {
                model.addAttribute("message", "El usuario ya existe.");
                return "register";
            }

        } catch (Exception e) {
            System.out.println("AIUDA ERRORRR: " + e.getMessage());
        }

        if(result.hasErrors()) {
            return "register";
        }

        userService.createUser(loginRequest);

        return "redirect:/users/login";
    }

    @GetMapping({"/users/login", "/"})
    public String loginForm() {
        return "login";
    }
    
    @PostMapping("/users/login")
    public String login() {
        return "redirect:/tasks";
    }
}
