package com.zosh.user.service.exception;

import com.zosh.user.service.payload.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex, WebRequest req){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), req.getDescription(false), LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
