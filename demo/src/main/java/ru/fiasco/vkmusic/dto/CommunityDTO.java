package ru.fiasco.vkmusic.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class CommunityDTO implements Serializable {
    private String link;
    private List<String> genre;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }
}
