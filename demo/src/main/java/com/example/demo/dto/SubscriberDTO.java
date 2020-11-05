package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class SubscriberDTO implements Serializable {
    private String nickname;
    private List<String> subscribesGenres;

    public SubscriberDTO(String nickname, List<String> subscribesGenres) {
        this.nickname = nickname;
        this.subscribesGenres = subscribesGenres;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getSubscribesGenres() {
        return subscribesGenres;
    }

    public void setSubscribesGenres(List<String> subscribesGenres) {
        this.subscribesGenres = subscribesGenres;
    }

    public SubscriberDTO() {
    }
}
