package com.example.demo.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PostDto {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");

    private String label;
    private ZonedDateTime date;
    private Integer likes;
    private String link;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "label='" + label + '\'' +
                ", date=" + date +
                ", likes=" + likes +
                ", link='" + link + '\'' +
                '}';
    }

    public String toBeautyString(){
        return label + '\n' +
                formatter.format(date) + '\n' +
                "лайков: " + likes + '\n' +
                "ссылка: " + link;
    }
}
