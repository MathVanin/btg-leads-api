package com.btg.leads_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();

        log.warn("Erros de validação: {}", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        "Falha ao validar",
                        errors,
                        request.getDescription(false).replace("uri=", "")));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            WebRequest request) {

        log.error("Integridade de dados violada: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto(
                        "Dados duplicados",
                        List.of(ex.getMostSpecificCause().getMessage()),
                        request.getDescription(false).replace("uri=", "")));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(
            Exception ex,
            WebRequest request) {

        log.error("Internal server error: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        "Internal server error",
                        List.of(ex.getMessage()),
                        request.getDescription(false).replace("uri=", "")));
    }
}