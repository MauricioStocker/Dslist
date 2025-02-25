package com.strockerdevs.dslist.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tb_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private boolean isMain; // Indica se essa é a imagem principal do jogo

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public Image() {
    }

    public Image(String url, boolean isMain, Game game) {
        this.url = url;
        this.isMain = isMain;
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Image other = (Image) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

   
}
