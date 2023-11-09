/**
 * ValidationException Class
 *
 * This utility class provides methods for handling validation exceptions and converting
 * constraint violations into a custom exception with error messages.
 */
package net.breezeware.cosspringproject.exception;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationException {
    /**
     * Handles constraint violations and converts them into a custom exception with error messages.
     *
     * @param fieldViolations A set of constraint violations.
     * @throws CustomException if constraint violations are found, containing error messages.
     */
    public static <T> void handlingException(Set<ConstraintViolation<T>> fieldViolations) throws CustomException {
        log.info("Entering handlingException");
        if (!fieldViolations.isEmpty()) {
            List<String> messages =
                    fieldViolations.stream().map(fieldViolation -> fieldViolation.getMessage()).toList();
            throw new CustomException(messages, HttpStatus.BAD_REQUEST);
        }

        log.info("Leaving handlingException with no error");
    }
}
