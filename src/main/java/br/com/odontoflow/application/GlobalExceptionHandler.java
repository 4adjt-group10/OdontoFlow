package br.com.odontoflow.application;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(),
                NOT_FOUND.value(),
                "Entity not found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(RescheduleException.class)
    public ResponseEntity<StandardError> handleRescheduleException(RescheduleException ex, HttpServletRequest request) {
        StandardError error = new StandardError(Instant.now(),
                BAD_REQUEST.value(),
                "Reschedule error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }
}