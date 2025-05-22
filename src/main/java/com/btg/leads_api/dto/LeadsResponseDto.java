package com.btg.leads_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class LeadsResponseDto {
    private String uuid;
    private String nome;
    private String email;
    private String telefone;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String dataCadastro;
}
