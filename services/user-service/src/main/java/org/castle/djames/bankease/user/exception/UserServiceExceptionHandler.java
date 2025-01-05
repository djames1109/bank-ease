package org.castle.djames.bankease.user.exception;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.common.exception.BankEaseException;
import org.castle.djames.bankease.common.model.Response;
import org.castle.djames.bankease.common.model.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Response<Object>> handleValidationException(MethodArgumentNotValidException exception) {
        log.error("Validation Exception encountered: {}", exception.getMessage());

        List<String> errorDetails = exception.getBindingResult().getFieldErrors()
                .stream().map(this::lookup)
                .filter(StringUtils::isNotBlank)
                .toList();

        var body = Response.builder()
                .status(ResponseStatus.ERROR)
                .code("BE_US_VE001")
                .message("Error encountered while validating request.")
                .errorDetails(errorDetails)
                .build();
        var response = ResponseEntity.badRequest().body(body);

        log.error("Error Response: {}", response);

        return response;
    }

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

    private String lookup(FieldError error) {
        if (Objects.isNull(error.getCodes()) || error.getCodes().length == 0)
            return null;

        String constraint = error.getCodes()[error.getCodes().length - 1];
        String[] split = error.getField().split("\\.");
        String field = split[split.length - 1];
        return String.format("%s.%s", constraint, field);
    }

    private String lookup(ObjectError error) {
        String constraint = error.getCodes()[error.getCodes().length - 1];
        String[] split = error.getObjectName().split("\\.");
        String field = split[split.length - 1];
        return String.format("%s.%s", constraint, field);
    }

}
