package com.example.demo.domain;

import com.example.demo.dto.CommunityDTO;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "community")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Community {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "genre", columnDefinition = "TEXT")
    private String genre;
    @Type(type = "list-array")
    @Column(name = "top_week", columnDefinition = "UUID[]")
    private List<UUID> topWeek = new ArrayList<>();
    @Type(type = "list-array")
    @Column(name = "top_month", columnDefinition = "UUID[]")
    private List<UUID> topMonth = new ArrayList<>();
    @Type(type = "list-array")
    @Column(name = "top_year", columnDefinition = "UUID[]")
    private List<UUID> topYear = new ArrayList<>();
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    public Community() {
    }


    public Community(CommunityDTO communityDTO){
        this.id = UUID.randomUUID();
        this.genre = communityDTO.getGenre();
        this.link = communityDTO.getLink();
        this.topWeek = communityDTO.getTopWeek();
        this.topMonth = communityDTO.getTopMonth();
        this.topYear = communityDTO.getTopYear();
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


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
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

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                ", topWeek=" + topWeek +
                ", topMonth=" + topMonth +
                ", topYear=" + topYear +
                ", link='" + link + '\'' +
                '}';
    }
}
