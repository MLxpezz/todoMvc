package com.todo.app.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.UserDto;
import com.todo.app.model.repository.UserRepository;
import com.todo.app.model.utils.UserMapper;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        return UserMapper.mapList(userEntities);
    }

    @Override
    public UserDto getUser(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow();

        return UserMapper.mapUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserEntity userEntity) {
        Optional<UserEntity> usOptional = userRepository.findById(id);

        if(usOptional.isPresent()) {
            UserEntity userEntity2 = usOptional.get();
            userEntity2.setAccountNoBlocked(userEntity.isAccountNoBlocked());
            userEntity2.setAccountNoExpired(userEntity.isAccountNoExpired());
            userEntity2.setCredentialsNoExpired(userEntity.isCredentialsNoExpired());
            userEntity2.setEnabled(userEntity.isEnabled());
            userEntity2.setPassword(userEntity.getPassword());
            userEntity2.setUsername(userEntity.getUsername());
            userEntity2.setTaskList(userEntity.getTaskList());

            userRepository.save(userEntity2);

            return UserMapper.mapUserDto(userEntity2);
        }

        return null;
    }

    @Override
    public String deleteUser(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        if(id != null && userOptional.isPresent()) {
            userRepository.deleteById(id);
            return "Usuario eliminado correctamente.";
        }

        return "el usuario no existe o no se encontro.";
    }

    @Override
    public UserDto createUser(UserEntity userEntity) {
        UserEntity newUser = UserEntity
        .builder()
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .accountNoBlocked(true)
        .accountNoExpired(true)
        .credentialsNoExpired(true)
        .isEnabled(true)
        .taskList(new ArrayList<>())
        .build();

        userRepository.save(newUser);

        return UserMapper.mapUserDto(newUser);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow();

        return UserMapper.mapUserDto(userEntity);
    }



}
