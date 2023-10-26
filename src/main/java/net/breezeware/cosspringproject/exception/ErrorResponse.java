package net.breezeware.cosspringproject.exception;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private int httpStatusCode;
    private String message;
    private List<String> errorDetails;
}
