package com.tcs.busbuddy.common.exception;

import com.tcs.busbuddy.common.dto.ApiResponse.ErrorDetail;
import com.tcs.busbuddy.common.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex) {
        return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                List.of(new ErrorDetail(ex.getCode(), ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleSpringValidation(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorDetail("VALIDATION_ERROR", err.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseBuilder.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of(new ErrorDetail("INTERNAL_SERVER_ERROR", ex.getMessage())));
    }
}
