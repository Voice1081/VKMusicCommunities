package ru.fiasco.vkmusic.dto;

import java.io.Serializable;
import java.util.List;

public class SubscriberDTO implements Serializable {
    private String nickname;
    private List<String> subscribersGenres;
    private int chatId;

    public SubscriberDTO(String nickname, List<String> subscribersGenres, int chatId) {
        this.nickname = nickname;
        this.subscribersGenres = subscribersGenres;
        this.chatId = chatId;
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

    public SubscriberDTO() {
    }
}
