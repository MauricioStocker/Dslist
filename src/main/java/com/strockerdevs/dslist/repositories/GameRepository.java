package com.strockerdevs.dslist.repositories;

import com.strockerdevs.dslist.entities.Game;
import com.strockerdevs.dslist.projections.GameMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT g FROM Game g LEFT JOIN FETCH g.images WHERE g.id = :gameId")
    Game findByIdWithImages(Long gameId);
}
