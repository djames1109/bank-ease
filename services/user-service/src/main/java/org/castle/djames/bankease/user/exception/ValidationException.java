package org.castle.djames.bankease.user.exception;

import org.castle.djames.bankease.common.exception.BankEaseException;
import org.springframework.http.HttpStatus;

public class ValidationException extends BankEaseException {
    private static final String ERROR_CODE = "BE_US_VE001";

    public ValidationException(String message) {
        super(ERROR_CODE, message, HttpStatus.BAD_REQUEST);
    }
}
