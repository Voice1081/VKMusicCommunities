package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class CommunityDTO implements Serializable {
    private String genre;
    private List<UUID> topWeek;
    private List<UUID> topMonth;
    private List<UUID> topYear;
    private String link;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CommunityDTO() {
    }

    public CommunityDTO(String genre, List<UUID> topWeek, List<UUID> topMonth, List<UUID> topYear, String link) {
        this.genre = genre;
        this.topWeek = topWeek;
        this.topMonth = topMonth;
        this.topYear = topYear;
        this.link = link;
    }

    public List<UUID> getTopWeek() {
        return topWeek;
    }

    public void setTopWeek(List<UUID> topWeek) {
        this.topWeek = topWeek;
    }

    public List<UUID> getTopMonth() {
        return topMonth;
    }

    public void setTopMonth(List<UUID> topMonth) {
        this.topMonth = topMonth;
    }

    public List<UUID> getTopYear() {
        return topYear;
    }

    public void setTopYear(List<UUID> topYear) {
        this.topYear = topYear;
    }
}
