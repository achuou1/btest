package com.backend.btest.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DeviceNotFoundException.class, UserNotFoundException.class, BookingNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception ex) {
        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getLocalizedMessage());
        body.setTitle("Not found");
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler({DeviceNotAvailableException.class})
    public ResponseEntity<?> handleDeviceNotAvailableException(Exception ex) {
        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(409), ex.getLocalizedMessage());
        body.setTitle("Device not available");
        return ResponseEntity.status(409).body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleDeviceNotBookedException(Exception ex) {
        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getLocalizedMessage());
        body.setTitle("Bad request");
        return ResponseEntity.badRequest().body(body);
    }

}