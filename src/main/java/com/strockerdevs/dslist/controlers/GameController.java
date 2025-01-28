package com.strockerdevs.dslist.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.strockerdevs.dslist.dto.GameDTO;
import com.strockerdevs.dslist.dto.GameMinDTO;
import com.strockerdevs.dslist.entities.Game;
import com.strockerdevs.dslist.services.GameService;

import java.util.List;

@Controller // Usar @Controller para servir páginas HTML
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/games")
    public String showGamePage(Model model) {
        // Teste inicial para evitar DTOs
        List<GameMinDTO> games = gameService.findAll();
        model.addAttribute("games", games);
        return "index";
    }

    // Endpoint para exibir detalhes de um jogo específico
    @GetMapping("/games/{id}")
    public String showGameDetails(@PathVariable Long id, Model model) {
        GameDTO game = gameService.findById(id);
        model.addAttribute("game", game);
        return "game-details"; // Nome do arquivo game-details.html em src/main/resources/templates
    }

    // Novo endpoint para buscar jogo por ID
    @GetMapping("/games/search")
    public String searchGameById(@RequestParam Long gameId, Model model) {
        GameDTO game = gameService.findById(gameId); // Busca o jogo pelo ID
        model.addAttribute("game", game);
        return "game-details"; // Redireciona para a página de detalhes
    }

    @GetMapping("/games/searchByName")
    public String searchGameByName(@RequestParam String title, Model model) {
        List<GameMinDTO> games = gameService.findByName(title); // Busca jogos pelo título
        model.addAttribute("games", games); // Adiciona os jogos encontrados ao modelo

        // Verifica se a lista está vazia e adiciona uma mensagem
        if (games.isEmpty()) {
            model.addAttribute("message", "Nenhum jogo encontrado com o título: " + title
        + " <br>Clique no botão BUSCAR sem escrever nada que traz para a tela inicial");
            
        }

        return "index"; // Reaproveita a página inicial para exibir os resultados
    }

}
