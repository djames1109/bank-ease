package org.castle.djames.bankease.common.exception;

import lombok.Getter;
import org.castle.djames.bankease.common.model.ErrorDetail;
import org.castle.djames.bankease.common.model.GenericErrorCode;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BankEaseException extends RuntimeException {

    private List<ErrorDetail> errorDetails;
    private HttpStatus httpStatus;

    public BankEaseException(String code, String message, HttpStatus httpStatus) {
        this(new ErrorDetail(code, message), httpStatus);
    }

    public BankEaseException(String message, HttpStatus httpStatus) {
        this(new ErrorDetail(GenericErrorCode.BANK_EASE_INTERNAL_ERROR, message), httpStatus);
    }

    public BankEaseException(ErrorDetail errorDetail, HttpStatus httpStatus) {
        super(errorDetail.getMessage());
        if (this.errorDetails == null) {
            errorDetails = new ArrayList<>();
        }
        errorDetails.add(errorDetail);
        this.httpStatus = httpStatus;
    }

}
