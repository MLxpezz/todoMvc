package com.todo.app.model.utils;

import java.util.ArrayList;
import java.util.List;

import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.UserDto;

public class UserMapper {

    public static UserDto mapUserDto(UserEntity userEntity) {
        return UserDto
        .builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .email(userEntity.getEmail())
        .list(userEntity.getTaskList() == null ? new ArrayList<>() : TaskMapper.mapListDtos(userEntity.getTaskList()))
        .build();
    }

    public static List<UserDto> mapList(List<UserEntity> userEntities) {
        return userEntities.stream()
        .map(user -> UserDto
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .list(user.getTaskList() == null ? new ArrayList<>() : TaskMapper.mapListDtos(user.getTaskList()))
                .build())
        .toList();
    }
    
}
