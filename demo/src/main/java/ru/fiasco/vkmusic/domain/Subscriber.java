package ru.fiasco.vkmusic.domain;

import ru.fiasco.vkmusic.dto.SubscriberDTO;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
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
    @Column(name = "subscribers_genres", columnDefinition = "TEXT[]")
    private List<String> subscribersGenres = new ArrayList<>();
    @Column(name = "chat_id", columnDefinition = "INTEGER")
    private long chatId;

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", subscribersGenres=" + subscribersGenres +
                ", chatId=" + chatId +
                '}';
    }

    public Subscriber() {
    }

    public Subscriber(UUID id, String nickname, List<String> subscribersGenres, int chatId) {
        this.id = id;
        this.nickname = nickname;
        this.subscribersGenres = subscribersGenres;
        this.chatId = chatId;
    }

    public Subscriber(String nickname, long chatId) {
        this.id = UUID.randomUUID();;
        this.nickname = nickname;
        this.subscribersGenres = new ArrayList<>();
        this.chatId = chatId;
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
}
