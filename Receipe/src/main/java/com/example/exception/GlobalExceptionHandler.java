package com.example.exception;

import com.demo.specification.model.ApiError;
import com.example.util.BusinessLogger;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecipeException.class)
    public ResponseEntity<ApiError> handleServiceError(final RecipeException serviceException) {
        final ApiError apiError = new ApiError();
        apiError.setErrorMessage(serviceException.getMessage());
        apiError.setErrorType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        apiError.setErrorTimestamp(LocalDate.now().toString());
        BusinessLogger.printErrorLogs(serviceException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleServiceError(final Exception exception) {
        final ApiError apiError = new ApiError();
        apiError.setErrorMessage(exception.getMessage());
        apiError.setErrorType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        apiError.setErrorTimestamp(LocalDate.now().toString());
        apiError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        BusinessLogger.printErrorLogs(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler({
                       HttpMessageNotReadableException.class,
                       JsonMappingException.class,
                       HttpMediaTypeNotSupportedException.class,
                       MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> exceptionHandler(final Exception exception) {
        final ApiError apiError = new ApiError();
        apiError.setErrorMessage(exception.getMessage());
        apiError.setErrorType(HttpStatus.BAD_REQUEST.getReasonPhrase());
        apiError.setErrorTimestamp(LocalDate.now().toString());
        apiError.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        BusinessLogger.printErrorLogs(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
