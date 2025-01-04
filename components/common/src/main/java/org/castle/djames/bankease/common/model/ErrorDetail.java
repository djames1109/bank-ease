package org.castle.djames.bankease.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDetail {

    private String code;
    private String message;
}
