package net.breezeware.cosspringproject.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ErrorResponse {
    private int httpStatusCode;
    private String message;
    private List<String> errorDetails;
}
