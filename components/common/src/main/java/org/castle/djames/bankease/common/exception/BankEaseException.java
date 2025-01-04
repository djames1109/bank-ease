package org.castle.djames.bankease.common.exception;

import lombok.Getter;
import org.castle.djames.bankease.common.model.ErrorDetail;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BankEaseException extends RuntimeException {

    private HttpStatus httpStatus;
    private String code;
    private String message;
    private List<ErrorDetail> errorDetails;

    public BankEaseException(String code, String message, HttpStatus httpStatus) {
        this(code, message, null, httpStatus);
    }

    public BankEaseException(String code, String message, ErrorDetail errorDetail, HttpStatus httpStatus) {
        super(message);
        if (this.errorDetails == null) {
            errorDetails = new ArrayList<>();
        }
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        errorDetails.add(errorDetail);
    }

}
