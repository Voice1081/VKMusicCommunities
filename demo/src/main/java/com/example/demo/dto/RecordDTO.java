package com.example.demo.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class RecordDTO implements Serializable {
    private String id;

    private ZonedDateTime date;

    private int likes;

    private String link;

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public int getLikes() {
        return likes;
    }

    public String getLink() {
        return link;
    }

}
