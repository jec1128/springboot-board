package com.ChoiSW.portfolio.error.exception;

import com.ChoiSW.portfolio.error.ErrorCode;

public class DuplicateIdException extends BusinessException{


    public DuplicateIdException(String message) {
        super(message, ErrorCode.DUPLICATE_ID);
    }
}
