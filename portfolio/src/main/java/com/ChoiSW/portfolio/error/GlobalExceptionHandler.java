package com.ChoiSW.portfolio.error;

import com.ChoiSW.portfolio.error.exception.AccessDeniedException;
import com.ChoiSW.portfolio.error.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {

        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        System.out.println(LocalTime.now()+" BusinessException " + e);

        System.out.println("errorCode : " + errorCode + " error response : " + response);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }



}
