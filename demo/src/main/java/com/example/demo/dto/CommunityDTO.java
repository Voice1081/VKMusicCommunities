package com.example.demo.dto;

import java.io.Serializable;
import java.util.UUID;

public class CommunityDTO implements Serializable {
    private String genre;
    private UUID[] topWeek;
    private UUID[] topMonth;
    private UUID[] topYear;
    private String link;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public UUID[] getTopWeek() {
        return topWeek;
    }

    public void setTopWeek(UUID[] topWeek) {
        this.topWeek = topWeek;
    }

    public UUID[] getTopMonth() {
        return topMonth;
    }

    public void setTopMonth(UUID[] topMonth) {
        this.topMonth = topMonth;
    }

    public UUID[] getTopYear() {
        return topYear;
    }

    public void setTopYear(UUID[] topYear) {
        this.topYear = topYear;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CommunityDTO(String genre, UUID[] topWeek, UUID[] topMonth, UUID[] topYear, String link) {
        this.genre = genre;
        this.topWeek = topWeek;
        this.topMonth = topMonth;
        this.topYear = topYear;
        this.link = link;
    }
}
