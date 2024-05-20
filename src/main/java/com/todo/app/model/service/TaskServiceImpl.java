package com.todo.app.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.todo.app.model.entities.TaskEntity;
import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.TaskDto;
import com.todo.app.model.repository.TaskRepository;
import com.todo.app.model.repository.UserRepository;
import com.todo.app.model.utils.TaskMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TaskDto> getTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        taskEntities.forEach(task -> {
            System.out.println("ID: " + task.getId());
        });
        return TaskMapper.mapListDtos(taskEntities);
    }

    @Override
    public TaskDto getTask(Long id) {
        TaskEntity task = taskRepository.findById(id).orElseThrow();

        return TaskMapper.mapTaskDto(task);
    }

    @Override
    public void updateTask(Long id, TaskDto taskDto) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(id);

        if(taskOptional.isPresent() && id != null) {
            TaskEntity taskEntity2 = taskOptional.get();
            taskEntity2.setTitle(taskDto.getTitle());
            taskEntity2.setDescription(taskDto.getDescription());

            taskRepository.save(taskEntity2);
        }
    }

    @Override
    public void deleteTask(Long id) {
        Optional<TaskEntity> taskOptional = taskRepository.findById(id);

        if(taskOptional.isPresent()) {
            System.out.println("hHOLHOLAHOLA");
            taskRepository.deleteById(id);;
        }
    }

    @Override
    public void createTask(TaskDto taskDto) {
    UserEntity userEntity = userRepository.findById(taskDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

    TaskEntity taskEntity = TaskEntity.builder()
            .title(taskDto.getTitle())
            .description(taskDto.getDescription())
            .createdAt(LocalDate.now())
            .isCompleted(false)
            .userTask(userEntity)
            .build();


    taskRepository.save(taskEntity);
}

    @Override
    public void deleteAllTasks(Long id) {
        taskRepository.customDeleteTask(id);
    }

    @Override
    public void complete(Long id) {
        Optional<TaskEntity> tOptional = taskRepository.findById(id);

        if(tOptional.isPresent()) {
            TaskEntity taskEntity = tOptional.get();
            taskEntity.setCompleted(true);

            taskRepository.save(taskEntity);
        }
    }

    @Override
    public List<TaskDto> getCompleteTasks() {
       return TaskMapper.mapListDtos(taskRepository.completeTasks());
    }

    @Override
    public List<TaskDto> getUnCompleteTasks() {
        return TaskMapper.mapListDtos(taskRepository.uncompleteTasks());
    }



}
