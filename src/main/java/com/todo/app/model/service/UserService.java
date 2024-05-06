package com.todo.app.model.service;

import java.util.List;

import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.UserDto;

public interface UserService {

    List<UserDto> getUsers();

    UserDto getUser(Long id);

    UserDto getUserByUsername(String username);

    UserDto updateUser(Long id, UserEntity userEntity);

    String deleteUser(Long id);

    UserDto createUser(UserEntity usereEntity);

}
