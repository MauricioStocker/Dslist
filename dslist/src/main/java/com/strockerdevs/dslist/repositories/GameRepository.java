package com.strockerdevs.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.strockerdevs.dslist.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
