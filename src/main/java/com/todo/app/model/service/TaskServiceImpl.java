package com.todo.app.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.model.entities.TaskEntity;
import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.TaskDto;
import com.todo.app.model.repository.TaskRepository;
import com.todo.app.model.repository.UserRepository;
import com.todo.app.model.utils.TaskMapper;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TaskDto> getTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();

        return TaskMapper.mapListDtos(taskEntities);
    }

    @Override
    public TaskDto getTask(Long id) {
        TaskEntity task = taskRepository.findById(id).orElseThrow();

        return TaskMapper.mapTaskDto(task);
    }

    @Override
    public TaskDto updateTask(Long id, TaskEntity taskEntity) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(id);

        if(taskOptional.isPresent() && id != null) {
            TaskEntity taskEntity2 = taskOptional.get();
            taskEntity2.setTitle(taskEntity.getTitle());
            taskEntity2.setDescription(taskEntity.getDescription());

            taskRepository.save(taskEntity2);

            return TaskMapper.mapTaskDto(taskEntity2);
        }

        return null;
    }

    @Override
    public String deleteTask(Long id) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(id);

        if(taskOptional.isPresent() && id != null) {
            taskRepository.deleteById(id);
            return "Tarea eliminada correctamente.";
        }

        return "No se encontro la tarea o no existe.";
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {

        UserEntity userEntity = userRepository.findById(taskDto.getUserId()).get();

        TaskEntity taskEntity2 = TaskEntity
        .builder()
        .title(taskDto.getTitle())
        .description(taskDto.getDescription())
        .createdAt(LocalDate.now())
        .isCompleted(false)
        .userTask(userEntity)
        .build();

        taskRepository.save(taskEntity2);

        return TaskMapper.mapTaskDto(taskEntity2);
    }

}
