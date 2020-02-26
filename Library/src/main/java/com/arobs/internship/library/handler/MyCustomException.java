package com.arobs.internship.library.handler;

public class MyCustomException extends RuntimeException {
    String message = null;

    public MyCustomException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
