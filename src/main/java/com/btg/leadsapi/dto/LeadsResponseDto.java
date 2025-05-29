package com.btg.leadsapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public record LeadsResponseDto(
        String uuid,
        String nome,
        String email,
        String telefone,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        String dataCadastro) {
}
