package com.library.management.exception;

import lombok.Getter;

public class NotAcceptableException extends BaseException{

    public NotAcceptableException(NotAcceptableExceptionMSG notAcceptableExceptionMSG) {
        super(406, notAcceptableExceptionMSG.getStatusCode(), notAcceptableExceptionMSG.getErrorMessage());
    }

    @Getter
    public enum NotAcceptableExceptionMSG {
        PASSWORD_DOES_NOT_MATCH("4060001", "Password does not match."),
        USER_ALREADY_EXIST("4060002", "User already exist. please try with new user");

        private final String statusCode;
        private final String errorMessage;

        NotAcceptableExceptionMSG(String statusCode, String errorMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
        }

    }
}
