package com.todo.app.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todo.app.model.entities.TaskEntity;
import com.todo.app.model.entities.dto.TaskDto;

@Service
public interface TaskService {

    List<TaskDto> getTasks();

    TaskDto getTask(Long id);

    TaskDto updateTask(Long id, TaskEntity taskEntity);

    String deleteTask(Long id);

    TaskDto createTask(TaskDto taskDto);

}
