package com.example.libraryManagementApp.exceptions;

import org.springframework.http.HttpStatus;

public class BookApiException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public BookApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BookApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
