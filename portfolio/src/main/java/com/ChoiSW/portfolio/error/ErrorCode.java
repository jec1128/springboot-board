package com.ChoiSW.portfolio.error;

public enum ErrorCode {

    // Common

    ACCESS_DENIED(403, "C001", "Access is denied"),
    INTERNAL_SERVER(500,"C002", "Internal Server Error"),
    REQUEST_REJECT(400,"C003","request reject"),
    NOT_EXISTED(400,"C004","Not existed"),
    //Register
    DUPLICATE_ID(400,"R001","Id is duplicate"),

    // Login
    LOGIN_INPUT_INVALID(400, "M001", "Login input is invalid"),


    //Argument
    METHOD_ARUGMENT_INVALID(400,"A001","argument is invalid");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}

