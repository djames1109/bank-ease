package org.castle.djames.bankease.common.util;

import org.castle.djames.bankease.common.model.Response;
import org.castle.djames.bankease.common.model.ResponseStatus;

public class GenericResponse {

    private static final String GENERIC_SUCCESS_CODE = "S001";
    private static final String GENERIC_ERROR_CODE = "E001";

    private GenericResponse() {
    }

    public static <T> Response<T> success(T body) {
        return Response.<T>builder().status(ResponseStatus.SUCCESS)
                .status(ResponseStatus.SUCCESS)
                .code(GENERIC_SUCCESS_CODE)
                .message("Success")
                .body(body)
                .build();
    }

    public static <T> Response<T> success(T body, String code, String message) {
        return Response.<T>builder().status(ResponseStatus.SUCCESS)
                .status(ResponseStatus.SUCCESS)
                .code(code)
                .message(message)
                .body(body)
                .build();
    }

    public static Response<Object> error() {
        return Response.builder()
                .status(ResponseStatus.ERROR)
                .code(GENERIC_ERROR_CODE)
                .message("Error")
                .body(null)
                .build();
    }

}
