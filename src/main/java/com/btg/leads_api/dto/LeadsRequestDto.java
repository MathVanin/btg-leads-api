package com.btg.leads_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.btg.leads_api.utils.Constants.*;

@Data
public class LeadsRequestDto {
    @NotBlank(message = NOME_INVALIDO)
    @Size(max = 100, message = NOME_INVALIDO)
    private String nome;

    @NotBlank(message = EMAIL_INVALIDO)
    @Size(max = 50, message = EMAIL_INVALIDO)
    @Email(message = EMAIL_INVALIDO)
    private String email;

    @NotBlank(message = TELEFONE_INVALIDO)
    @Pattern(regexp = "\\d{10,11}", message = TELEFONE_INVALIDO)
    private String telefone;

    @NotBlank(message = CPF_INVALIDO)
    @Pattern(regexp = "\\d{11}", message = CPF_INVALIDO)
    private String cpf;
}
