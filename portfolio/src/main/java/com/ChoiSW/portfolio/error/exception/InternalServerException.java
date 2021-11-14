package com.ChoiSW.portfolio.error.exception;

import com.ChoiSW.portfolio.error.ErrorCode;

public class InternalServerException extends BusinessException{

    public InternalServerException(String message) {

        super(message, ErrorCode.INTERNAL_SERVER);
    }
}
