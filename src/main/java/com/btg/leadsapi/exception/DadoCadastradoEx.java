package com.btg.leadsapi.exception;

public class DadoCadastradoEx extends RuntimeException {
    public DadoCadastradoEx(String dado) {
        super(dado + " já cadastrado");
    }
}
