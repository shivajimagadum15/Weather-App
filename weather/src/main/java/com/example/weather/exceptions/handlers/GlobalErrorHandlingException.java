package com.example.weather.exceptions.handlers;

import com.example.weather.exceptions.CustomException;
import com.example.weather.exceptions.RecordNotFoundException;
import com.example.weather.exceptions.response.ErrorResponse;
import com.example.weather.helper.MessageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalErrorHandlingException extends ResponseEntityExceptionHandler {

    Logger _logger = LogManager.getLogger(GlobalErrorHandlingException.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException cve){
        return getErrorResponseResponseEntity(MessageHelper.get("invalid.zip.code"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(RecordNotFoundException ex) {
        return getErrorResponseResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException ex) {
        return getErrorResponseResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(String message, HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(httpStatus.value());
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        _logger.error(message);
        return new ResponseEntity<>(response, httpStatus);
    }
}
