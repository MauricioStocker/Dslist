package com.strockerdevs.dslist.services;

import com.strockerdevs.dslist.entities.Game;
import com.strockerdevs.dslist.entities.Image;
import com.strockerdevs.dslist.repositories.GameRepository;
import com.strockerdevs.dslist.repositories.ImageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public ImageService(GameRepository gameRepository, ImageRepository imageRepository,
            CloudinaryService cloudinaryService) {
        this.gameRepository = gameRepository;
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    public Image saveImage(Long gameId, MultipartFile imageFile, boolean isMain) throws IOException {
        logger.info("Iniciando o salvamento da imagem para o jogo ID: {}", gameId);

        // 1. Validar o tipo de arquivo
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Apenas arquivos de imagem são permitidos.");
        }

        // 2. Validar o tamanho do arquivo (exemplo: 5MB)
        long maxSize = 5 * 1024 * 1024; // 5MB
        if (imageFile.getSize() > maxSize) {
            throw new IllegalArgumentException("O tamanho máximo permitido para a imagem é 5MB.");
        }

        // 3. Verificar se o jogo existe
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));

        // 4. Enviar a imagem para o Cloudinary e obter a URL
        String imageUrl = cloudinaryService.uploadImage(imageFile);
        logger.info("Imagem enviada para Cloudinary com sucesso: {}", imageUrl);

        // 5. Verificar se este jogo já tem imagens cadastradas
        Optional<Image> firstImage = imageRepository.findFirstByGameId(gameId);
        if (firstImage.isPresent()) {
            if (isMain) {
                imageRepository.updateIsMainFalseByGameId(gameId);
                logger.info("Nova imagem principal definida para o jogo ID: {}", gameId);
            }
        } else {
            isMain = true;
            logger.info("Primeira imagem cadastrada para o jogo ID: {}. Marcada como principal.", gameId);
        }

        // 6. Criar e salvar a imagem associada ao jogo
        Image image = new Image();
        image.setUrl(imageUrl);
        image.setGame(game);
        image.setMain(isMain);
        imageRepository.save(image);
        logger.info("Imagem salva com sucesso no jogo ID: {}. URL: {}", gameId, imageUrl);
        return image;
    }

}
