package com.library.management.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
public class NotFoundException extends BaseException {

    public NotFoundException(UserNotFound userNotFound) {
        super(404, userNotFound.getStatusCode(), userNotFound.getErrorMessage());
    }

    public NotFoundException(NotFound notFound) {
        super(404, notFound.getStatusCode(), notFound.getErrorMessage());
    }

    @Getter
    public enum NotFound {
        BOOK_NOT_FOUND("4040100", "Book not found");
        private final String statusCode;
        private final String errorMessage;

        NotFound(String statusCode, String errorMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
        }
    }


    @Getter
    public enum UserNotFound {
        USER_NOT_FOUND("4040200", "User not found");


        private final String statusCode;
        private final String errorMessage;

        UserNotFound(String statusCode, String errorMessage) {
            this.statusCode = statusCode;
            this.errorMessage = errorMessage;
        }
    }
}
