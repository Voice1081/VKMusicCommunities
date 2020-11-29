package com.example.demo.domain;

import com.example.demo.dto.CommunityDTO;
import com.example.demo.dto.SubscriberDTO;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subscriber")
public class Subscriber {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "nickname", columnDefinition = "TEXT")
    private String nickname;
    @Type(type = "list-array")
    @Column(name = "subscribers_genres", columnDefinition = "TEXT[]")
    private List<String> subscribersGenres = new ArrayList<>();
    @Column(name = "chat_id", columnDefinition = "INTEGER")
    private long chatId;
    @Column(name = "bot_state", columnDefinition = "VARCHAR")
    private String botState;

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", subscribersGenres=" + subscribersGenres +
                ", chatId=" + chatId +
                ", botState='" + botState + '\'' +
                '}';
    }

    public Subscriber() {
    }

    public Subscriber(UUID id, String nickname, List<String> subscribersGenres, int chatId, String botState) {
        this.id = id;
        this.nickname = nickname;
        this.subscribersGenres = subscribersGenres;
        this.chatId = chatId;
        this.botState = botState;
    }

    public Subscriber(String nickname, long chatId) {
        this.id = UUID.randomUUID();;
        this.nickname = nickname;
        this.subscribersGenres = new ArrayList<>();
        this.chatId = chatId;
        this.botState = "START";
    }

    public Subscriber(SubscriberDTO subscriberDTO){
        this.id = UUID.randomUUID();
        this.nickname = subscriberDTO.getNickname();
        this.subscribersGenres = subscriberDTO.getSubscribersGenres();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public long getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getBotState() {
        return botState;
    }

    public void setBotState(String botState) {
        this.botState = botState;
    }
}
