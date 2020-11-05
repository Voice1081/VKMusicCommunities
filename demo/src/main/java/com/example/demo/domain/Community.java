package com.example.demo.domain;

import com.example.demo.dto.CommunityDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "community")
public class Community {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "genre", columnDefinition = "TEXT")
    private String genre;
    @Column(name = "top_week", columnDefinition = "UUID[]")
    private UUID[] topWeek;
    @Column(name = "top_month", columnDefinition = "UUID[]")
    private UUID[] topMonth;
    @Column(name = "top_year", columnDefinition = "UUID[]")
    private UUID[] topYear;
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                ", topWeek=" + Arrays.toString(topWeek) +
                ", topMonth=" + Arrays.toString(topMonth) +
                ", topYear=" + Arrays.toString(topYear) +
                ", link='" + link + '\'' +
                '}';
    }

    public Community() {
    }

    public Community(UUID id, String genre, UUID[] topWeek, UUID[] topMonth, UUID[] topYear, String link) {
        this.id = id;
        this.genre = genre;
        this.topWeek = topWeek;
        this.topMonth = topMonth;
        this.topYear = topYear;
        this.link = link;
    }

    public Community(CommunityDTO communityDTO){
        new Community(UUID.randomUUID(), communityDTO.getGenre(), communityDTO.getTopWeek(), communityDTO.getTopMonth(),
                communityDTO.getTopYear(), communityDTO.getLink());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
}
