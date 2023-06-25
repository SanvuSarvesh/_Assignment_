package com.example.GreenStich.Exceptions;

public class BadCredentialsExceptions extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public BadCredentialsExceptions(String msg) {
        super(msg);
    }
}
