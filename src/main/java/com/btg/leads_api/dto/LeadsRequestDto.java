package com.btg.leads_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.btg.leads_api.utils.Constants.*;

/**
 * DTO para criacao de um lead.
 * Contem anotacoes para validação assegurando que os
 * dados sejam validos.
 */
public record LeadsRequestDto(
        @NotBlank(message = NOME_INVALIDO)
        @Size(max = 100, message = NOME_INVALIDO)
        String nome,

        @NotBlank(message = EMAIL_INVALIDO)
        @Size(max = 50, message = EMAIL_INVALIDO)
        @Email(message = EMAIL_INVALIDO)
        String email,

        @NotBlank(message = TELEFONE_INVALIDO)
        @Pattern(regexp = "\\d{10,11}", message =
                TELEFONE_INVALIDO)
        String telefone,

        @NotBlank(message = CPF_INVALIDO)
        @Pattern(regexp = "\\d{11}", message = CPF_INVALIDO)
        String cpf) {

}
