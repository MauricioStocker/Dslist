package com.strockerdevs.dslist.repositories;

import com.strockerdevs.dslist.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
  

    // Método para buscar todas as pessoas com uma role específica
    List<Pessoa> findByRole(Pessoa.Role role);

    Optional<Pessoa> findByEmail(String email);
    
}
