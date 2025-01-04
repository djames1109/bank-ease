package org.castle.djames.bankease.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.common.exception.BankEaseException;
import org.castle.djames.bankease.common.model.Response;
import org.castle.djames.bankease.common.model.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler({BankEaseException.class})
    public <T extends BankEaseException> ResponseEntity<Response<Object>> handleUserServiceException(T exception) {
        log.error("{} Exception encountered: {}", exception.getClass(), exception.getMessage());

        var body = Response.builder()
                .status(ResponseStatus.ERROR)
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
        var response = ResponseEntity.status(exception.getHttpStatus()).body(body);

        log.error("Error Response: {}", response);

        return response;
    }
}
