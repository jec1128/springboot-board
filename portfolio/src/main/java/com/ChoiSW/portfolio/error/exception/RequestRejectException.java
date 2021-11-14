package com.ChoiSW.portfolio.error.exception;

import com.ChoiSW.portfolio.error.ErrorCode;

public class RequestRejectException extends BusinessException{

    public RequestRejectException(String message) {
        super(message, ErrorCode.REQUEST_REJECT);
    }
}
