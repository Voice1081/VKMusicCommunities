package com.example.demo.web.rest.errors.exceptions;

import org.springframework.http.HttpStatus;

public class BadGatewayException extends ApiException {
    public BadGatewayException(String message) {
        super(message, HttpStatus.BAD_GATEWAY);
    }
}
