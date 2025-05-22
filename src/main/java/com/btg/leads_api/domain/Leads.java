package com.btg.leads_api.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "leads", schema = "btg_leads")
public class Leads {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false,
            unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "str_name", nullable = false, length = 100)
    private String nome;

    @Column(name = "str_email", nullable = false, length = 50)
    private String email;

    @Column(name = "str_phone", nullable = false, length = 11)
    private String telefone;

    @Column(name = "str_cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "dt_insert", nullable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = java.time.LocalDateTime.now();
        this.uuid = java.util.UUID.randomUUID();
    }
}
