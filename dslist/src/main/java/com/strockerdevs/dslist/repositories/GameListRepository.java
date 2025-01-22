package com.strockerdevs.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strockerdevs.dslist.entities.GameList;

public interface GameListRepository extends JpaRepository<GameList, Long> {
}
