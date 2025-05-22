package com.btg.leads_api.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LeadsResponseDto {
    private String uuid;
    private String nome;
    private String email;
    private String telefone;
    private String dataCriacao;
}
