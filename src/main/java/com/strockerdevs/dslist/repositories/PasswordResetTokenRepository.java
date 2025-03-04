package com.strockerdevs.dslist.repositories;

import com.strockerdevs.dslist.entities.PasswordResetToken;
import com.strockerdevs.dslist.entities.Pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token); // Busca token pelo valor

    // Consulta para encontrar o token ativo para o usuário
    @Query("SELECT prt FROM PasswordResetToken prt WHERE prt.user = :user AND prt.expiryDate > CURRENT_TIMESTAMP")
    Optional<PasswordResetToken> findActiveTokenByUser(@Param("user") Pessoa user);

}