package com.example.demo.dto;

import java.io.Serializable;


public class LinkDTO implements Serializable {
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LinkDTO() {
    }

    public LinkDTO(String link) {
        this.link = link;
    }
}