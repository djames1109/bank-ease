package org.castle.djames.bankease.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Response<T> {

    private ResponseStatus status;
    private String code;
    private String message;
    private T body;
    private List<ErrorDetail> errorDetails;

    public Response<T> addError(ErrorDetail errorDetail) {
        if (this.errorDetails == null) {
            var errorList = new ArrayList<ErrorDetail>();
            errorList.add(errorDetail);
            this.errorDetails = errorList;
            return this;
        }

        this.errorDetails.add(errorDetail);
        return this;
    }

    public Response<T> addError(String code, String message) {
        if (this.errorDetails == null) {
            var errorList = new ArrayList<ErrorDetail>();
            errorList.add(new ErrorDetail(code, message));
            this.errorDetails = errorList;
            return this;
        }

        this.errorDetails.add(new ErrorDetail(code, message));
        return this;
    }
}
