package com.todo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.app.model.entities.dto.TaskDto;
import com.todo.app.model.entities.dto.UserDto;
import com.todo.app.model.service.TaskService;
import com.todo.app.model.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class TasksController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/tasks")
    public String getTasks(HttpSession session, Model model) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        
        UserDto userEntity = userService.getUser(userDto.getId());
        model.addAttribute("userFinal", userEntity);

        return "tasks";
    }

    @GetMapping("/tasks/create")
    public String createTaskForm(Model model) {
        model.addAttribute("taskDto", new TaskDto());
        return "createTask";
    }

    @PostMapping("/tasks/create")
    public String createTask(HttpSession session, Model model, @Valid @ModelAttribute("taskDto") TaskDto taskDto, BindingResult result) {

        if (result.hasErrors()) {
            return "createTask";
        }
    
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        taskDto.setUserId(userDto.getId());
        taskService.createTask(taskDto);

        return "redirect:/tasks";
    }

    
    
}
