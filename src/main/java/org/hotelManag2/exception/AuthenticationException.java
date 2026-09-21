package org.hotelManag2.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String msg){
        super(msg);
    }
}