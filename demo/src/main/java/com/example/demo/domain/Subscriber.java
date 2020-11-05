package com.example.demo.domain;

import com.example.demo.dto.CommunityDTO;
import com.example.demo.dto.SubscriberDTO;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Subscriber {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "nickname", columnDefinition = "TEXT")
    private String nickname;
    @Type(type = "list-array")
    @Column(name = "subscribes_genres", columnDefinition = "TEXT[]")
    private List<String> subscribesGenres = new ArrayList<>();

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", subscribesGenres=" + subscribesGenres +
                '}';
    }

    public Subscriber() {
    }

    public Subscriber(UUID id, String nickname, List<String> subscribesGenres) {
        this.id = id;
        this.nickname = nickname;
        this.subscribesGenres = subscribesGenres;
    }

    public Subscriber(SubscriberDTO subscriberDTO){
        this.id = UUID.randomUUID();
        this.nickname = subscriberDTO.getNickname();
        this.subscribesGenres = subscriberDTO.getSubscribesGenres();
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

    public List<String> getSubscribesGenres() {
        return subscribesGenres;
    }

    public void setSubscribesGenres(List<String> subscribesGenres) {
        this.subscribesGenres = subscribesGenres;
    }
}
