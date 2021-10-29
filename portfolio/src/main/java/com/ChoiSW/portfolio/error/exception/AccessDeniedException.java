package com.ChoiSW.portfolio.error.exception;

import com.ChoiSW.portfolio.error.ErrorCode;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String message) {
        super(message, ErrorCode.ACCESS_DENIED);
    }
}
