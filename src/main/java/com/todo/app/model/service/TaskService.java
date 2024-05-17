package com.todo.app.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todo.app.model.entities.dto.TaskDto;

@Service
public interface TaskService {

    List<TaskDto> getTasks();

    TaskDto getTask(Long id);

    void updateTask(Long id, TaskDto taskDto);

    void deleteTask(Long id);

    void createTask(TaskDto taskDto);

    void deleteAllTasks(Long id);

}
