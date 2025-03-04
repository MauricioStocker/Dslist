package com.strockerdevs.dslist.repositories;

import com.strockerdevs.dslist.entities.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT g FROM Produto g LEFT JOIN FETCH g.images WHERE g.id = :gameId")
    Produto findByIdWithImages(Long gameId);

    @Query("SELECT g FROM Produto g WHERE " +
            "(:genre IS NULL OR :genre = '' OR g.genre LIKE %:genre%) AND " +
            "(:platform IS NULL OR :platform = '' OR g.platforms LIKE %:platform%) AND " +
            "(:year IS NULL OR g.year = :year)")
    List<Produto> findByFilters(
            @Param("genre") String genre,
            @Param("platform") String platform,
            @Param("year") Integer year);
}
