package com.ChoiSW.portfolio.error.exception;

import com.ChoiSW.portfolio.error.ErrorCode;

public class MethodArgumentInvalidException extends BusinessException{

    public MethodArgumentInvalidException(String message) {

        super(message, ErrorCode.METHOD_ARUGMENT_INVALID);
    }
}
