package com.todo.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.app.model.entities.dto.TaskDto;
import com.todo.app.model.entities.dto.UserDto;
import com.todo.app.model.service.TaskService;
import com.todo.app.model.service.UserService;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping({"/", ""})
    public String getTasks(Model model) {
        int taskComplete  = (int) taskService.getTasks().stream().filter(task -> task.isCompleted()).count();

        model.addAttribute("countTaskComplete", taskComplete);
        return "tasks";
    }

    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("taskDto", new TaskDto());
        return "createTask";
    }

    @PostMapping("/create")
    public String createTask(@Valid @ModelAttribute("taskDto") TaskDto taskDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "createTask";
        }

        taskService.createTask(taskDto);

        return "redirect:/tasks";
    }


    @GetMapping("/edit")
    public String editTaskForm(@RequestParam(name = "id", required = true)  Long id, Model model) {

        try {
            TaskDto taskDto = taskService.getTask(id);

            model.addAttribute("taskDto", taskDto);

        } catch (Exception e) {
            System.out.println("AIUDA:" + e.getMessage());
            return "redirect:/tasks";
        }

        return "editTask";
    }

    @PostMapping("/edit")
    public String editTask(@RequestParam(name = "id", required = true) Long id,
                            @Valid @ModelAttribute TaskDto taskDto,
                            BindingResult result) {
        try {

            if(result.hasErrors()) {
                return "/editTask";
            }

            taskService.updateTask(id, taskDto);
            
        } catch (Exception e) {
            System.out.println("AIUDA: " + e.getMessage());
        }
        
        return "redirect:/tasks";
    }

    @GetMapping("/delete")
    public String deleteTasks(@RequestParam("id") Long id) {
        try {
            taskService.deleteTask(id);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/tasks";
    }

    @GetMapping("/deleteTasks")
    public String deleteAllTasks(@RequestParam Long id) {
        taskService.deleteAllTasks(id);
        return "redirect:/tasks";
    }

    @GetMapping("/complete")
    public String completeTask(@RequestParam Long id) {
        taskService.complete(id);
        return "redirect:/tasks";
    }

    @ModelAttribute("tasks")
    public List<TaskDto> tasksProvider(@RequestParam(name = "complete", required = false) Boolean complete) {

        if(complete != null && complete) {
            return taskService.getCompleteTasks();
        } else if(complete != null && !complete) {
            return taskService.getUnCompleteTasks();
        }

        return userProvider().getList();
    }
    
    
    @ModelAttribute("userFinal")
    public UserDto userProvider() {

        UserDto userDto = null;

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication != null && authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();
                userDto = userService.getUserByUsername(user.getUsername());
            } 

        } catch (Exception e) {
            System.out.println("Error al recuperar el usuario autenticado: " + e.getMessage());
        }

        return userDto;
    }
}
