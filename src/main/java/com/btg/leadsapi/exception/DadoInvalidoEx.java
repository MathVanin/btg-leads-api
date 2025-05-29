package com.btg.leadsapi.exception;

public class DadoInvalidoEx extends RuntimeException {
    public DadoInvalidoEx(String dado) {
        super(dado + " inválido");
    }
}
