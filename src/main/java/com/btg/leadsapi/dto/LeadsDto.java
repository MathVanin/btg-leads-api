package com.btg.leadsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeadsDto {
    private String name;
    private String email;
    private String telephone;
    private String cpf;
}
