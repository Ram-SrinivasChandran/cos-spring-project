/**
 * ExceptionHandling Class
 *
 * This class provides global exception handling for Spring-based web applications.
 * It extends ResponseEntityExceptionHandler to handle and respond to exceptions in a structured manner.
 */
package net.breezeware.cosspringproject.exception;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    /**
     * customExceptionHandler - Handles custom exceptions to the CustomException class.
     *
     * Constructs an ErrorResponse object based on the exception details and returns a ResponseEntity
     * containing the error response. The error response includes the HTTP status code, error message, and error details.
     *
     * @param customException The custom exception that needs to be handled.
     * @return ResponseEntity<ErrorResponse> An HTTP response entity containing an ErrorResponse object with error details.
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandler(final CustomException customException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatusCode(customException.getHttpStatus().value());
        errorResponse.setMessage(customException.getHttpStatus().name();

        if ((Objects.isNull(customException.getMessage()) || customException.getMessage().isBlank())
                && !customException.getMessages().isEmpty()) {
            errorResponse.setErrorDetails(customException.getMessages());
        } else {
            errorResponse.setErrorDetails(List.of(customException.getMessage()));
        }

        return new ResponseEntity<>(errorResponse, customException.getHttpStatus());
    }

    /**
     * handleHttpRequestMethodNotSupported - Handles HTTP request methods that are not supported by the application.
     *
     * Returns a ResponseEntity with a message indicating that the requested action is wrong and a status of NOT_ACCEPTABLE (406).
     *
     * @param ex The HttpRequestMethodNotSupportedException exception.
     * @param headers The HTTP headers of the response.
     * @param status The HTTP status code.
     * @param request The WebRequest object.
     * @return ResponseEntity<Object> An HTTP response entity with a message indicating the incorrect action and a status of NOT_ACCEPTABLE.
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("The Action given is wrong", HttpStatus.NOT_ACCEPTABLE);
    }
}
