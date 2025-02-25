package com.strockerdevs.dslist.controlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strockerdevs.dslist.dto.GameListDTO;

import com.strockerdevs.dslist.services.GameListService;
import com.strockerdevs.dslist.services.GameService;

@RestController
@RequestMapping(value = "/lists")
public class GameListControler {

    @Autowired
    private GameListService gameListService;

    @SuppressWarnings("unused")
    @Autowired
    private GameService gameService;

    @GetMapping // buscando todos os games registrados
    public List<GameListDTO> findAll() {
        List<GameListDTO> result = gameListService.findAll();
        return result;
    }

    /*
     * @GetMapping(value = "/{listId}/games")
     * public List<GameMinDTO> findByList(@PathVariable Long listId) {
     * List<GameMinDTO> result = gameService.findByLIst(listId);
     * return result;
     * }
     * 
     * /* @GetMapping(value = "/{id}") // configurado no endpoint para que buscamos
     * pelo id do jogo
     * public GameDTO findById(@PathVariable Long id) {
     * GameDTO result = gameService.findById(id);
     * return result;
     * }
     */

}
