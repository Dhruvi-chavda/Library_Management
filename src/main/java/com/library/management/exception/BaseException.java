package com.library.management.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private final int status;
    private final String errorCode;
    private final String errorMessage;
}
