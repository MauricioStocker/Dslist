package com.strockerdevs.dslist.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.strockerdevs.dslist.dto.GameDTO;
import com.strockerdevs.dslist.dto.GameMinDTO;
import com.strockerdevs.dslist.entities.Game;
import com.strockerdevs.dslist.projections.GameMinProjection;
import com.strockerdevs.dslist.repositories.GameRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


    @Value("${game.upload-dir}") // A pasta onde as imagens serão armazenadas (pode ser configurada no application.properties)
    private String uploadDir;

    public Game saveGame(GameDTO gameDto) throws IOException {
        // Processar a imagem
        String imgName = saveImage(gameDto.getImageFile());

        // Criar a entidade Game com os dados do DTO
        Game game = new Game();
        game.setTitle(gameDto.getTitle());
        game.setScore(gameDto.getScore());
        game.setYear(gameDto.getYear());
        game.setGenre(gameDto.getGenre());
        game.setPlatforms(gameDto.getPlatforms());
        game.setImgUrl(imgName); // Salvar o nome da imagem no banco
        game.setShortDescription(gameDto.getShortDescription());
        game.setLongDescription(gameDto.getLongDescription());

        return gameRepository.save(game); // Salvar no banco de dados
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename(); // Gerar nome único para o arquivo
        Path targetLocation = Paths.get(uploadDir).resolve(fileName);
        Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING); // Salvar o arquivo
        return "/images/" + fileName; // Retornar o caminho relativo para o banco de dados
    }

    @Transactional(readOnly = true) // faz com que a busca ou a não escrica, so leitura seja mais rapida
    public GameDTO findById(Long id) {
        Game result = gameRepository.findById(id).get();// demos um get pois o id do game que traz é um objeto, assim
                                                        // ele encontra os dados necessarios
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

    /*
     * @Transactional(readOnly = true)
     * public List<GameMinDTO> findByLIst(Long listId) {
     * List<GameMinProjection> result = gameRepository.searchByList(listId);
     * List<GameMinDTO> dto = result.stream().map(x -> new GameMinDTO(x)).toList();
     * // o stream é responsavel por
     * // transforma um objeto em outro
     * return dto;
     * }
     */

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByName(String title) {
        List<Game> result = gameRepository.findByTitleContainingIgnoreCase(title); // Usa o método personalizado
        return result.stream().map(GameMinDTO::new).toList(); // Converte para DTO
    }

}
