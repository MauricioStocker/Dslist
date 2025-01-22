package com.strockerdevs.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strockerdevs.dslist.dto.GameMinDTO;
import com.strockerdevs.dslist.entities.Game;
import com.strockerdevs.dslist.repositories.GameRepository;

@Service
public class GameService {
    

    @Autowired
    private GameRepository gameRepository;

    public List<GameMinDTO> findAll(){

       List<Game> result =  gameRepository.findAll();
       List<GameMinDTO> dto = result.stream().map(x -> new GameMinDTO(x)).toList(); // o stream é responsavel por transforma um objeto em outro

       return dto;
    }
}
