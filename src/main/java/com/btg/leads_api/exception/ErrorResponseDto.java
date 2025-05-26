package com.btg.leads_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Dto para resposta de erro na API.
 * @param message - mensagem de erro
 * @param details - detalhes do erro
 * @param timestamp - data e hora do erro
 * @param path - caminho da requisição que causou o erro
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDto(
        String message,
        List<String> details,
        Instant timestamp,
        String path
) {
    public ErrorResponseDto(String message, List<String> details, String path) {
        this(message, details, Instant.now(), path);
    }
}
