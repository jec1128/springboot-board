package com.ChoiSW.portfolio.error.exception;

import com.ChoiSW.portfolio.error.ErrorCode;

public class NotExistedException extends BusinessException{

    public NotExistedException(String message) {

        super(message, ErrorCode.NOT_EXISTED);
    }
}
