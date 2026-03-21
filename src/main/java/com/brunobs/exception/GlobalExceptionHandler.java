package com.brunobs.exception;

import com.brunobs.shared.validation.FieldError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Chaves do messages_en.properties (Padrão Inglês)
    private static final String MSG_VALIDATION_ERROR = "error.global.validation.failed";
    private static final String MSG_READ_ERROR = "error.global.request.readable";
    private static final String MSG_FORMAT_ERROR = "error.global.request.format";
    private static final String MSG_INTEGRITY_ERROR = "error.global.data.integrity";
    private static final String MSG_NOT_FOUND_ERROR = "error.global.resource.not.found";
    private static final String MSG_GENERIC_ERROR = "error.global.internal.server";

    // Define o MediaType com UTF-8 explicitamente para evitar caracteres corrompidos
    private static final MediaType JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                getMessage(MSG_VALIDATION_ERROR),
                ex.getValidationResult().getErrors()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(JSON_UTF8).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String key = (ex.getCause() instanceof InvalidFormatException) ? MSG_FORMAT_ERROR : MSG_READ_ERROR;
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.name(), getMessage(key));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(JSON_UTF8).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Integrity error detected: {}", ex.getMostSpecificCause().getMessage());
        ApiError error = new ApiError(HttpStatus.CONFLICT.name(), getMessage(MSG_INTEGRITY_ERROR));
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(JSON_UTF8).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        String fullPath = request.getRequestURL().toString();
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.name(),
                getMessage(MSG_NOT_FOUND_ERROR),
                List.of(new FieldError(ex.getResourcePath(), fullPath))
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(JSON_UTF8).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        log.error("Unexpected error in application.", ex);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.name(), getMessage(MSG_GENERIC_ERROR));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(JSON_UTF8).body(error);
    }

    private String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
