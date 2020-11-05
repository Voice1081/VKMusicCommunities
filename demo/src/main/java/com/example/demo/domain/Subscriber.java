package com.example.demo.domain;

import com.example.demo.dto.CommunityDTO;
import com.example.demo.dto.SubscriberDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "subscriber")
public class Subscriber {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "nickname", columnDefinition = "TEXT")
    private String nickname;
    @Column(name = "subscribes_genres", columnDefinition = "TEXT[]")
    private String[] subscribesGenres;

    public Subscriber() {
    }

    public Subscriber(UUID id, String nickname, String[] subscribesGenres) {
        this.id = id;
        this.nickname = nickname;
        this.subscribesGenres = subscribesGenres;
    }

    public Subscriber(SubscriberDTO subscriberDTO){
        new Subscriber(UUID.randomUUID(), subscriberDTO.getNickname(), subscriberDTO.getSubscribesGenres());
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

    public String[] getSubscribesGenres() {
        return subscribesGenres;
    }

    public void setSubscribesGenres(String[] subscribesGenres) {
        this.subscribesGenres = subscribesGenres;
    }
}
