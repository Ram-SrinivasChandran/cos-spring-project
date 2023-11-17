package net.breezeware.cosspringproject.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * CustomException is an extended RuntimeException class used to handle custom
 * exceptions in the application.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException {

    private String message; // A single error message
    private HttpStatus httpStatus; // The HTTP status code associated with the exception
    private List<String> messages; // List of error messages

    /**
     * Constructs a CustomException with a single error message and an HTTP status
     * code.
     * @param message    A single error message describing the exception.
     * @param httpStatus The HTTP status code associated with the exception.
     */
    public CustomException(String message, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a CustomException with a list of error messages and an HTTP status
     * code.
     * @param messages   A list of error messages describing the exception.
     * @param httpStatus The HTTP status code associated with the exception.
     */
    public CustomException(List<String> messages, HttpStatus httpStatus) {
        super();
        this.messages = messages;
        this.httpStatus = httpStatus;
    }
}
