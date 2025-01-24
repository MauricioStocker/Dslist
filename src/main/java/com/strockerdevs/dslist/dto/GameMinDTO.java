package com.strockerdevs.dslist.dto;

import com.strockerdevs.dslist.entities.Game;
import com.strockerdevs.dslist.projections.GameMinProjection;

public class GameMinDTO {

    private Long id;
    private String title;
    private Integer year;
    private String imgUrl;
    private String shortDescription;

    public GameMinDTO() {
    }

    public GameMinDTO(Game entity) {
        id = entity.getId();
        title = entity.getTitle();
        year = entity.getYear();
        imgUrl = entity.getImgUrl();
        shortDescription = entity.getShortDescription();
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

    public GameMinDTO(GameMinProjection entityProjection) {
        id = entityProjection.getId();
        title = entityProjection.getTitle();
        year = entityProjection.getGameYear();
        imgUrl = entityProjection.getImgUrl();
        shortDescription = entityProjection.getShortDescription();
    }
    

}
