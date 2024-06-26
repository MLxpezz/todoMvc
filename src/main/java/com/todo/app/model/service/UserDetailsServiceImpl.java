package com.todo.app.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todo.app.exceptions.UserNotConfirmedExeption;
import com.todo.app.model.entities.UserEntity;
import com.todo.app.model.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(usernameOrEmail)
        .orElseGet(() -> userRepository.findUserEntityByEmail(usernameOrEmail)
                                        .orElseThrow(() -> new UsernameNotFoundException("El usuario no fue encontrado.")));

        if(!userEntity.isEnabled()) {
            throw new UserNotConfirmedExeption("Tu cuenta aun no esta confirmada.");
        }

        List<SimpleGrantedAuthority> list = new ArrayList<>();

        return new User(
            userEntity.getUsername(),
            userEntity.getPassword(),
            userEntity.isEnabled(),
            userEntity.isAccountNoExpired(),
            userEntity.isCredentialsNoExpired(),
            userEntity.isAccountNoBlocked(),
            list
        );
    }

    
    
}
