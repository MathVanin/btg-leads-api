package com.btg.leads_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para resposta de erro.
 * @param timestamp
 * @param status
 * @param error
 * @param message
 * @param path
 * @param details
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<ValidationError> details
) {
    public record ValidationError(String field,
                                  String message) {
    }

    public static ErrorResponseDto create(int status,
                                          String error,
                                          String message,
                                          String path,
                                          List<ValidationError> details) {
        return new ErrorResponseDto(
                LocalDateTime.now(),
                status,
                error,
                message,
                path,
                details
        );
    }
}
