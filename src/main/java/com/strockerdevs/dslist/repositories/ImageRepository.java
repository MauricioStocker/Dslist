package com.strockerdevs.dslist.repositories;

import com.strockerdevs.dslist.entities.Image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    // Busca a primeira imagem associada ao jogo específico
    Optional<Image> findFirstByGameId(Long gameId);

    // Atualiza todas as imagens de um jogo para não serem principais
    @Modifying
    @Transactional
    @Query("UPDATE Image i SET i.isMain = false WHERE i.game.id = :gameId")
    void updateIsMainFalseByGameId(@Param("gameId") Long gameId);
}
