package com.strockerdevs.dslist.services;

import com.strockerdevs.dslist.dto.GameDTO;
import com.strockerdevs.dslist.dto.GameMinDTO;
import com.strockerdevs.dslist.entities.Produto;
import com.strockerdevs.dslist.entities.Image;
import com.strockerdevs.dslist.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository gameRepository;

    @Autowired
    ImageService imageService;

    @Transactional
    public Produto saveGameWithImages(GameDTO gameDto, List<MultipartFile> imageFiles) throws IOException {
        // Criação do objeto Game com as informações de gameDto
        Produto game = new Produto();
        game.setTitle(gameDto.getTitle());
        game.setGenre(gameDto.getGenre());
        game.setPlatforms(gameDto.getPlatforms());
        game.setScore(gameDto.getScore());
        game.setShortDescription(gameDto.getShortDescription());
        game.setLongDescription(gameDto.getLongDescription());
        game.setYear(gameDto.getYear());

        // Salvar o jogo no banco
        game = gameRepository.save(game);

        // Salvar as imagens utilizando o ImageService
        for (MultipartFile imageFile : imageFiles) {
            // Aqui o ImageService cuida do upload da imagem e da persistência
            imageService.saveImage(game.getId(), imageFile, false); // O false indica que a imagem não é principal
        }

        return game;
    }

    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        Produto result = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        return new GameDTO(result);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
        List<Produto> result = gameRepository.findAll();
        return result.stream().map(GameMinDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByName(String title) {
        List<Produto> result = gameRepository.findByTitleContainingIgnoreCase(title);
        return result.stream().map(GameMinDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public GameDTO findByIdWithImages(Long id) {
        Produto result = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        GameDTO dto = new GameDTO(result);

        // Adicionando as URLs das imagens no DTO
        List<String> imageUrls = result.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
        dto.setImgUrls(imageUrls); // Supondo que você tenha um campo imageUrls no DTO

        return dto;
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByFilters(String genre, String platform, Integer year) {
        List<Produto> result = gameRepository.findByFilters(genre, platform, year);
        return result.stream().map(GameMinDTO::new).toList();
    }

}