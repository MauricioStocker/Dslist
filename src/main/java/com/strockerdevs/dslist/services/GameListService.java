package com.strockerdevs.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strockerdevs.dslist.dto.GameListDTO;
import com.strockerdevs.dslist.entities.GameList;
import com.strockerdevs.dslist.repositories.GameListRepository;


@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

  /*   @Transactional(readOnly = true)// faz com que a busca ou a não escrica, so leitura seja mais rapida
    public GameDTO findById(Long id) {
        Game result = gameRepository.findById(id).get();// demos um get pois o id do game que traz é um objeto, assim ele encontra os dados necessarios
        GameDTO dto = new GameDTO(result);
        return dto;
    }*/

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
        List<GameList> result = gameListRepository.findAll();
        List<GameListDTO> dto = result.stream().map(x -> new GameListDTO(x)).toList(); // o stream é responsavel por
                                                                   // transforma um objeto em outro
        return dto;
    }

}
