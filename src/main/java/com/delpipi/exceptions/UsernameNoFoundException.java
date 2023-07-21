package com.delpipi.exceptions;

public class UsernameNoFoundException extends Exception {
    
    public UsernameNoFoundException(){
        super();
    }

    public UsernameNoFoundException(String message){
        super(message);
    }

    public UsernameNoFoundException( String message, Throwable t){
        super(message, t);
    }
}
