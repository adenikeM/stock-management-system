package com.techfirm.stock.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String message;
    private String description;
    private int errorCode;


    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ErrorResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public ErrorResponse setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public static ErrorResponse buildErrorResponse(String description, HttpStatus status) {
        return new ErrorResponse()
                .setMessage(status.getReasonPhrase())
                .setErrorCode(status.value())
                .setDescription(description);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", description='" + description + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}

