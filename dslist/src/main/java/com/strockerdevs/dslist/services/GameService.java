package com.strockerdevs.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strockerdevs.dslist.dto.GameDTO;
import com.strockerdevs.dslist.dto.GameMinDTO;
import com.strockerdevs.dslist.entities.Game;
import com.strockerdevs.dslist.projections.GameMinProjection;
import com.strockerdevs.dslist.repositories.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)// faz com que a busca ou a não escrica, so leitura seja mais rapida
    public GameDTO findById(Long id) {
        Game result = gameRepository.findById(id).get();// demos um get pois o id do game que traz é um objeto, assim ele encontra os dados necessarios
        GameDTO dto = new GameDTO(result);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
        List<Game> result = gameRepository.findAll();
        List<GameMinDTO> dto = result.stream().map(x -> new GameMinDTO(x)).toList(); // o stream é responsavel por
                                                                   // transforma um objeto em outro
        return dto;
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByLIst(Long listId) {
        List<GameMinProjection> result = gameRepository.searchByList(listId);
        List<GameMinDTO> dto = result.stream().map(x -> new GameMinDTO(x)).toList(); // o stream é responsavel por
                                                                   // transforma um objeto em outro
        return dto;
    }

}
