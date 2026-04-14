package com.chat.aj.chatbackend.Exceptions;

public class EmailSendException extends RuntimeException {
    public EmailSendException(String message) {
        super(message);
    }
}
