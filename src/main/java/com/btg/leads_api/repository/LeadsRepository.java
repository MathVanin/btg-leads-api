package com.btg.leads_api.repository;

import com.btg.leads_api.domain.Leads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeadsRepository extends JpaRepository<Leads, Long> {
    Optional<Leads> findByEmail(String email);

    Optional<Leads> findByTelefone(String telefone);

    Optional<Leads> findByCpf(String cpf);
}
