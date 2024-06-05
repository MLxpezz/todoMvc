package com.todo.app.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.app.exceptions.PasswordNotMatchesException;
import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.entities.dto.LoginRequest;
import com.todo.app.model.entities.dto.UpdateUserRequest;
import com.todo.app.model.entities.dto.UserDto;
import com.todo.app.model.repository.UserRepository;
import com.todo.app.model.utils.UserMapper;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

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
    public void updateUser(UpdateUserRequest updateUserRequest) {
        UserEntity userEntity = userRepository
                                    .findById(updateUserRequest.id())
                                    .orElseThrow(() -> new UsernameNotFoundException("El usuario no se encontro."));

        if(!passwordEncoder.matches(updateUserRequest.currentPassword(), userEntity.getPassword())) {
            throw new PasswordNotMatchesException("Las contraseñas proporcionadas no coinciden.");
        }

        userEntity.setUsername(updateUserRequest.username());
        userEntity.setPassword(passwordEncoder.encode(updateUserRequest.newPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);

        if(id != null && userOptional.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public void createUser(LoginRequest loginRequest) {
        UserEntity newUser = UserEntity
        .builder()
        .username(loginRequest.username())
        .email(loginRequest.email())
        .password(passwordEncoder.encode(loginRequest.password()))
        .accountNoBlocked(true)
        .accountNoExpired(true)
        .credentialsNoExpired(true)
        .isEnabled(false)
        .build();

        userRepository.save(newUser);
        mailService.sendMailConfirmAccount(newUser.getEmail(), newUser.getId());
    }

    @Override
    public UserDto getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow();

        return UserMapper.mapUserDto(userEntity);
    }

    @Override
    public void setIsEnabled(Long id) {
        if(id != null) {
            UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("El usuario no se encontro"));

            userEntity.setEnabled(true);
            userRepository.save(userEntity);
        }
    }

    



}
