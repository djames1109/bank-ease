package org.castle.djames.bankease.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Response<T> {

    private ResponseStatus status;
    private String code;
    private String message;
    private T body;
    private List<String> errorDetails;

}
