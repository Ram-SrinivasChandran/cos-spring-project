package net.breezeware.cosspringproject.exception;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    private int httpStatusCode;
    private String message;
    private List<String> errorDetails;
}
