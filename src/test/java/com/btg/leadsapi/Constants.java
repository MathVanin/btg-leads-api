package com.btg.leadsapi;

import java.time.LocalDate;
import java.util.UUID;

public class Constants {
    public static final UUID UUID_VALIDO = UUID.randomUUID();
    public static final String NOME_VALIDO = "Teste Nome";
    public static final String EMAIL_VALIDO = "teste@teste.com";
    public static final String TELEFONE_VALIDO = "11234567894";
    public static final String CPF_VALIDO = "67980693043";
    public static final LocalDate DATA_CADASTRO = LocalDate.now();
    public static final String NOME_INVALIDO = "a";
    public static final String EMAIL_INVALIDO = "teste@teste";
    public static final String TELEFONE_INVALIDO = "123";
    public static final String CPF_INVALIDO = "12345678901";
}
