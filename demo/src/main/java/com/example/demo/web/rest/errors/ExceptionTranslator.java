package com.example.demo.web.rest.errors;

import com.example.demo.web.rest.errors.exceptions.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorMessage> handleClientException(final ApiException e) {
        return ResponseEntity.status(e.getHttpStatus().value()).body(new ErrorMessage(e.getHttpStatus().value(), e.getMessage()));
    }

    public static final class ErrorMessage {
        private int code;
        private String message;

        public ErrorMessage() {
        }

        public ErrorMessage(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
