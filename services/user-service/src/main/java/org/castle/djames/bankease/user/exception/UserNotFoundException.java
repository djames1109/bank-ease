package org.castle.djames.bankease.user.exception;

import org.castle.djames.bankease.common.exception.BankEaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BankEaseException {
    private static final String ERROR_CODE = "BE_US_E001";

    public UserNotFoundException(String message) {
        super(ERROR_CODE, message, HttpStatus.NOT_FOUND);
    }
}
