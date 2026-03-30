package com.brunobs.exception;

import com.brunobs.message.general.GlobalMessages;
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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    private static final MediaType JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);

    private final GlobalMessages globalMessages;

    public GlobalExceptionHandler(GlobalMessages globalMessages) {
        this.globalMessages = globalMessages;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                globalMessages.validationFailed(),
                ex.getValidationResult().getErrors()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(JSON_UTF8).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED.name(),
                globalMessages.userAccessDenaid(),
                ex.getValidationResult().getErrors()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(JSON_UTF8).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String key = (ex.getCause() instanceof InvalidFormatException) ?
                globalMessages.requestFormat() :
                globalMessages.requestReadable();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(JSON_UTF8).body(
                new ApiError(HttpStatus.BAD_REQUEST.name(), key)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Integrity error detected: {}", ex.getMostSpecificCause().getMessage());
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.name(),
                globalMessages.dataIntegrity()

        );
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(JSON_UTF8).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        String fullPath = request.getRequestURL().toString();
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.name(),
                globalMessages.resourceNotFound(),
                List.of(new FieldError(ex.getResourcePath(), fullPath))
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(JSON_UTF8).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String field = ex.getName();
        String requiredType = ex.getRequiredType().getSimpleName();


        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                globalMessages.validationFailed(),
                List.of(new FieldError(field, globalMessages.typeMismatch(field, requiredType)))
        );

        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        log.error("Unexpected error in application.", ex);
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                globalMessages.internalServerError(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(JSON_UTF8).body(error);
    }

}
