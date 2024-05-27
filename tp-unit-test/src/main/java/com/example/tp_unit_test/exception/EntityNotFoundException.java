package com.example.tp_unit_test.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message){
        super(message);
    }
}
