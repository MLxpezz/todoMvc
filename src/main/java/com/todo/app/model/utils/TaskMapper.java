package com.todo.app.model.utils;

import java.util.List;

import com.todo.app.model.entities.TaskEntity;
import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.TaskDto;

public class TaskMapper {
    
    public static TaskDto mapTaskDto(TaskEntity taskEntity) {
        return TaskDto
        .builder()
        .title(taskEntity.getTitle())
        .description(taskEntity.getDescription())
        .createdAt(taskEntity.getCreatedAt())
        .isCompleted(taskEntity.isCompleted())
        .userId(taskEntity.getUserTask().getId())
        .build();
    }

    public static List<TaskDto> mapListDtos(List<TaskEntity> taskEntities) {
        return taskEntities
        .stream()
        .map(task -> TaskDto
        .builder()
        .title(task.getTitle())
        .description(task.getDescription())
        .createdAt(task.getCreatedAt())
        .isCompleted(task.isCompleted())
        .userId(task.getUserTask().getId())
        .build())
        .toList();
    }

    public static List<TaskEntity> mapDtoToEntity(List<TaskDto> taskDtos, UserEntity userEntity) {
        return taskDtos
        .stream()
        .map(taskDto -> TaskEntity
        .builder()
        .title(taskDto.getTitle())
        .description(taskDto.getDescription())
        .createdAt(taskDto.getCreatedAt())
        .isCompleted(taskDto.isCompleted())
        .userTask(userEntity)
        .build())
        .toList();
    }
}
