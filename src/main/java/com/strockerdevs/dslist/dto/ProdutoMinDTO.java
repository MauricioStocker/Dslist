package com.strockerdevs.dslist.dto;

import com.strockerdevs.dslist.entities.Produto;
import com.strockerdevs.dslist.entities.Image;
import com.strockerdevs.dslist.projections.GameMinProjection;

public class ProdutoMinDTO {

    private Long id;
    private String title;
    private Integer year;
    private String imgUrl;
    private String shortDescription;

    public ProdutoMinDTO() {
    }

    public ProdutoMinDTO(Produto entity) {
        id = entity.getId();
        title = entity.getTitle();
        year = entity.getYear();
        imgUrl = entity.getImages().stream()  // Pegando a URL da imagem principal
                           .filter(Image::isMain)
                           .map(img -> img.getUrl())
                           .findFirst()
                           .orElse(null);  // Caso não haja imagem principal, retorna null
        shortDescription = entity.getShortDescription();
    }

    public ProdutoMinDTO(GameMinProjection entityProjection) {
        id = entityProjection.getId();
        title = entityProjection.getTitle();
        year = entityProjection.getGameYear();
        imgUrl = entityProjection.getImgUrl();
        shortDescription = entityProjection.getShortDescription();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}
