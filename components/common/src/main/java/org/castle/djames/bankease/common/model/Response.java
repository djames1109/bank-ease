package org.castle.djames.bankease.common.model;

import lombok.Data;

import java.util.List;

@Data
public class Response {

    private ResponseStatus status;
    private String code;
    private String message;
    private List<ErrorDetail> errorDetails;

}
