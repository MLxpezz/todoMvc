package com.todo.app.exceptions;

public class PasswordNotMatchesException extends RuntimeException{
    
    public PasswordNotMatchesException(String message) {
        super(message);
    }
}
