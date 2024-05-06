package com.todo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import com.todo.app.model.entities.dto.UserDto;
import com.todo.app.model.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@SessionScope
public class TasksController {

    @GetMapping("/tasks")
    public String getTasks(HttpSession session) {
        session.getAttribute("userDto");
        return "tasks";
    }

    @GetMapping("/tasks/create")
    public String createTask() {
        return "createTask";
    }

    
    
}
