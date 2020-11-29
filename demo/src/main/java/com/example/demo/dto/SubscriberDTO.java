package com.example.demo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class SubscriberDTO implements Serializable {
    private String nickname;
    private List<String> subscribersGenres;
    private int chatId;
    private String state;

    public SubscriberDTO(String nickname, List<String> subscribersGenres, int chatId, String state) {
        this.nickname = nickname;
        this.subscribersGenres = subscribersGenres;
        this.chatId = chatId;
        this.state = state;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getSubscribersGenres() {
        return subscribersGenres;
    }

    public void setSubscribersGenres(List<String> subscribersGenres) {
        this.subscribersGenres = subscribersGenres;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public SubscriberDTO() {
    }
}
