package com.strockerdevs.dslist.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.strockerdevs.dslist.dto.ProdutoDTO;
import com.strockerdevs.dslist.dto.ProdutoMinDTO;
import com.strockerdevs.dslist.entities.Produto;
import com.strockerdevs.dslist.entities.Image;
import com.strockerdevs.dslist.services.ProdutoService;
import com.strockerdevs.dslist.services.ImageService;

import java.io.IOException;
import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService gameService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/games")
    public String showGamePage(Model model) {
        List<ProdutoMinDTO> games = gameService.findAll();
        model.addAttribute("games", games);

        // Verifica se o usuário é ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin); // Adiciona a flag ao modelo

        return "index";
    }

    // Método para exibir os detalhes de um jogo
    @GetMapping("/games/{id}")
    public String getGameDetails(@PathVariable("id") Long id, Model model) {
        ProdutoDTO game = gameService.findById(id);
        model.addAttribute("game", game);
        return "game-details";
    }

    // Método para buscar um jogo pelo ID
    @GetMapping("/games/search")
    public String searchGameById(@RequestParam Long gameId, Model model) {
        // Atualizado para usar findByIdWithImages, que inclui as imagens
        ProdutoDTO game = gameService.findByIdWithImages(gameId); // Busca o jogo com imagens
        model.addAttribute("game", game); // Adiciona o jogo ao modelo
        return "game-details"; // Retorna a view de detalhes do jogo
    }

    // Método para buscar jogos por nome
    @GetMapping("/games/searchByName")
    public String searchGameByName(@RequestParam String title, Model model) {
        // Busca os jogos pelo nome
        List<ProdutoMinDTO> games = gameService.findByName(title);
        model.addAttribute("games", games); // Adiciona os jogos ao modelo
        if (games.isEmpty()) { // Verifica se não encontrou jogos
            model.addAttribute("message", "Nenhum jogo encontrado com o título: " + title
                    + " <br>Clique no botão BUSCAR sem escrever nada que traz para a tela inicial");
        }
        return "index"; // Retorna a view inicial
    }

    // Método para exibir a página de adicionar um jogo
    @GetMapping("/games/add")
    public String showAddGamePage(Model model) {
        // Verifica se o usuário tem a role ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/games"; // Redireciona para a página inicial se não for ADMIN
        }
        return "add-game"; // Retorna a view para adicionar um jogo
    }

    // Método para adicionar um jogo e suas imagens
    @PostMapping("/add")
    public ResponseEntity<Produto> addGame(
            @RequestParam("game") String gameJson,
            @RequestParam("imageFiles") List<MultipartFile> imageFiles) throws IOException {
        // Converte o JSON recebido para um objeto GameDTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProdutoDTO gameDto = objectMapper.readValue(gameJson, ProdutoDTO.class);

        // Chama o GameService para salvar o jogo com as imagens
        Produto savedGame = gameService.saveGameWithImages(gameDto, imageFiles);

        // Retorna o jogo salvo como resposta HTTP
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGame);
    }

    // Método para adicionar uma imagem a um jogo específico
    @PostMapping("/games/{id}/addImage")
    public ResponseEntity<Image> addImageToGame(@PathVariable Long id,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        // Chama o ImageService para salvar a imagem para o jogo com ID específico
        Image savedImage = imageService.saveImage(id, imageFile, false); // O 'false' pode ser um indicador de que é a
                                                                         // imagem não principal
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage); // Retorna a imagem salva
    }
    @GetMapping("/games/filter")
    public String filterGames(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) Integer year,
            Model model) {

        // Chama o serviço para buscar jogos com base nos filtros
        List<ProdutoMinDTO> games = gameService.findByFilters(genre, platform, year);

        // Adiciona os jogos filtrados ao modelo
        model.addAttribute("games", games);

        // Verifica se nenhum jogo foi encontrado
        if (games.isEmpty()) {
            model.addAttribute("message", "Nenhum jogo encontrado com os filtros selecionados.");
        }

        // Retorna a view inicial
        return "index";
    }
}
