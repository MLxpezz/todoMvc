package com.todo.app.model.service;

import java.util.List;

import com.todo.app.model.entities.dto.LoginRequest;
import com.todo.app.model.entities.dto.UpdateUserRequest;
import com.todo.app.model.entities.dto.UserDto;

public interface UserService {

    List<UserDto> getUsers();

    UserDto getUser(Long id);

    UserDto getUserByUsername(String username);

    void updateUser(UpdateUserRequest updateUserRequest);

    void deleteUser(Long id);

    void createUser(LoginRequest loginRequest);

}
