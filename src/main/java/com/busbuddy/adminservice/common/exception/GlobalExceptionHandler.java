package com.busbuddy.adminservice.common.exception;

import com.busbuddy.adminservice.common.constants.ErrorCodes;
import com.busbuddy.adminservice.common.dto.ApiResponse.ErrorDetail;
import com.busbuddy.adminservice.common.util.ResponseBuilder;
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
        return ResponseBuilder.buildErrorResponse(
                mapToStatus(ex.getCode()).value(),
                List.of(new ErrorDetail(ex.getCode(), ex.getMessage()))
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidation(ValidationException ex) {
        return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST.value(),
                List.of(new ErrorDetail(ex.getCode(), ex.getMessage())));
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<?> handleTechnical(TechnicalException ex) {
        return ResponseBuilder.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of(new ErrorDetail(ex.getCode(), ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleSpringValidation(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorDetail(ErrorCodes.VALIDATION_ERROR, err.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseBuilder.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of(new ErrorDetail(ErrorCodes.INTERNAL_SERVER_ERROR, "Something went wrong, please try again")));
    }

    private HttpStatus mapToStatus(String code) {
        return switch (code) {
            case ErrorCodes.BUS_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ErrorCodes.DUPLICATE_BUS -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
