package com.strockerdevs.dslist.repositories;

import com.strockerdevs.dslist.entities.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT g FROM Game g LEFT JOIN FETCH g.images WHERE g.id = :gameId")
    Game findByIdWithImages(Long gameId);

    @Query("SELECT g FROM Game g WHERE " +
            "(:genre IS NULL OR :genre = '' OR g.genre LIKE %:genre%) AND " +
            "(:platform IS NULL OR :platform = '' OR g.platforms LIKE %:platform%) AND " +
            "(:year IS NULL OR g.year = :year)")
    List<Game> findByFilters(
            @Param("genre") String genre,
            @Param("platform") String platform,
            @Param("year") Integer year);
}
