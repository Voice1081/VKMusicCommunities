package com.example.demo.dto;

import java.io.Serializable;
import java.util.UUID;

public class SubscriberDTO implements Serializable {
    private String nickname;
    private String[] subscribesGenres;

    public SubscriberDTO(String nickname, String[] subscribesGenres) {
        this.nickname = nickname;
        this.subscribesGenres = subscribesGenres;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String[] getSubscribesGenres() {
        return subscribesGenres;
    }

    public void setSubscribesGenres(String[] subscribesGenres) {
        this.subscribesGenres = subscribesGenres;
    }
}
