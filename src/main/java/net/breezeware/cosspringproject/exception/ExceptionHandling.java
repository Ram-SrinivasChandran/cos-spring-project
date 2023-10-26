package net.breezeware.cosspringproject.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandler(final CustomException customException){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setHttpStatusCode(customException.getHttpStatus().value());
        errorResponse.setMessage(customException.getHttpStatus().name());
        if((Objects.isNull(customException.getMessage())||customException.getMessage().isBlank())&&!customException.getMessages().isEmpty()){
            errorResponse.setErrorDetails(customException.getMessages());
        }
        else {
            errorResponse.setErrorDetails(List.of(customException.getMessage()));
        }
        return new ResponseEntity<>(errorResponse,customException.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("The Action given is wrong",HttpStatus.NOT_ACCEPTABLE);
    }
}
