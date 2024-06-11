package com.todo.app.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotConfirmedExeption extends AuthenticationException{
    public UserNotConfirmedExeption(String message) {
        super(message);
    }
}
