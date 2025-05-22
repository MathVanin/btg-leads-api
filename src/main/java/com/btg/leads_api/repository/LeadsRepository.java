package com.btg.leads_api.repository;

import com.btg.leads_api.domain.Leads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeadsRepository extends JpaRepository<Leads, Long> {
    Optional<Leads> findByEmail(String email);

    Optional<Leads> findByTelefone(String telefone);

    Optional<Leads> findByCpf(String cpf);

    @Query("SELECT l FROM Leads l WHERE " +
            "(:uuid IS NULL OR l.uuid = :uuid) AND " +
            "(:nome IS NULL OR l.nome LIKE %:nome%) AND " +
            "(:email IS NULL OR l.email LIKE %:email%) AND " +
            "(:telefone IS NULL OR l.telefone LIKE %:telefone%) AND " +
            "(:cpf IS NULL OR l.cpf LIKE %:cpf%) AND " +
            "(:dataCadastro IS NULL OR l.dataCadastro = :dataCadastro)")
    Page<Leads> findWithFilters(Pageable pageable, UUID uuid, String nome, String email, String telefone,
                                String cpf, LocalDate dataCadastro);
}
