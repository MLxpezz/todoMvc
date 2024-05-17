package com.todo.app.model.utils;

import java.util.List;

import com.todo.app.model.entities.TaskEntity;
import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.TaskDto;

public class TaskMapper {
    
    public static TaskDto mapTaskDto(TaskEntity taskEntity) {
        return TaskDto
        .builder()
        .id(taskEntity.getId())
        .title(taskEntity.getTitle())
        .description(taskEntity.getDescription())
        .createdAt(taskEntity.getCreatedAt())
        .isCompleted(taskEntity.isCompleted())
        .build();
    }

    public static List<TaskDto> mapListDtos(List<TaskEntity> taskEntities) {
        return taskEntities
        .stream()
        .map(task -> TaskDto
        .builder()
        .id(task.getId())
        .title(task.getTitle())
        .description(task.getDescription())
        .createdAt(task.getCreatedAt())
        .isCompleted(task.isCompleted())
        .build())
        .toList();
    }

    public static List<TaskEntity> mapDtoToEntity(List<TaskDto> taskDtos, UserEntity userEntity) {
        return taskDtos
        .stream()
        .map(taskDto -> TaskEntity
        .builder()
        .id(taskDto.getId())
        .title(taskDto.getTitle())
        .description(taskDto.getDescription())
        .createdAt(taskDto.getCreatedAt())
        .isCompleted(taskDto.isCompleted())
        .build())
        .toList();
    }
}
