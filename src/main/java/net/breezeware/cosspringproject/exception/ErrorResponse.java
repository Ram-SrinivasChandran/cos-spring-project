/**
 * ErrorResponse Class This class represents an error response structure to be
 * used in exception handling. It contains details such as HTTP status code,
 * error message, and a list of error details.
 */
package net.breezeware.cosspringproject.exception;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    /**
     * The HTTP status code associated with the error.
     */
    private int httpStatusCode;

    /**
     * The error message or status description.
     */
    private String message;

    /**
     * A list of error details providing additional information about the error.
     */
    private List<String> errorDetails;
}
