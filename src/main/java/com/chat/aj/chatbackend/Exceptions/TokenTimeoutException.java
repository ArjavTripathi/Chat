package com.chat.aj.chatbackend.Exceptions;

public class TokenTimeoutException extends RuntimeException {
    public TokenTimeoutException(String message) {
        super(message);
    }
}
