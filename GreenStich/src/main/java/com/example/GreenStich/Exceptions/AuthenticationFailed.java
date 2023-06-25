package com.example.GreenStich.Exceptions;

public class AuthenticationFailed extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public AuthenticationFailed(String msg) {
        super(msg);
    }
}
