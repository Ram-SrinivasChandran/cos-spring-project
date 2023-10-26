package net.breezeware.cosspringproject.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException{

    private String message;
    private HttpStatus httpStatus;
    private List<String> messages;
    public CustomException(String message,HttpStatus httpStatus) {
        super();
        this.message=message;
        this.httpStatus=httpStatus;
    }
    public CustomException(List<String> messages,HttpStatus httpStatus) {
        super();
        this.messages=messages;
        this.httpStatus=httpStatus;
    }

}
