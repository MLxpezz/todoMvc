package com.todo.app.controller;

import com.todo.app.exceptions.PasswordNotMatchesException;
import com.todo.app.model.entities.dto.LoginRequest;
import com.todo.app.model.entities.dto.UpdateUserRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String createUser(Model model) {
        model.addAttribute("loginRequest", new LoginRequest(null, null));
        return "register";
    }

    
    @PostMapping("/register")
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

    @GetMapping({"/login", "/"})
    public String loginForm() {
        return "login";
    }
    
    @PostMapping({"/login", "/"})
    public String login() {
        return "redirect:/tasks";
    }

    @GetMapping("/edit")
    public String updateUser(@RequestParam(name = "id", required = true) Long id, Model model) {

        UserDto userDto = null;

        try {

            userDto = userService.getUser(id);
            model.addAttribute("user", 
                                new UpdateUserRequest(id, 
                                userDto.getUsername(),
                    null, 
                null));

        } catch (Exception e) {
            System.out.println("Ocurrio un error: " + e.getMessage());
            return "redirect:/tasks";
        }

        return "editUser";
    }

    @PostMapping("/edit")
    public String updateUser(@Valid @ModelAttribute("user") UpdateUserRequest updateUserRequest,
                            BindingResult result,
                            Model model) {

        try {
            
            if(result.hasErrors()) {
                return "editUser";
            }

            userService.updateUser(updateUserRequest);

        } catch (PasswordNotMatchesException e) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            System.out.println("ERROR: " + e.getMessage());
            return "editUser";
        }

        return "redirect:/tasks";
    }
    
}
