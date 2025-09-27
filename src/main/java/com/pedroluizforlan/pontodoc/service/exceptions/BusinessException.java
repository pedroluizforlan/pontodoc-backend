package com.pedroluizforlan.pontodoc.service.exceptions;

public class BusinessException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public BusinessException(String message){
        super(message);
    }
}