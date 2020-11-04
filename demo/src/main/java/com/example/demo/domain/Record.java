package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "test_table")
public class Record {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "date", columnDefinition = "TIMESTAMPTZ")
    private ZonedDateTime date;
    @Column(name = "likes", columnDefinition = "INTEGER")
    private int likes;
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", date=" + date +
                ", likes=" + likes +
                ", link='" + link + '\'' +
                '}';
    }

    public void setId(UUID id) {
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

    public UUID getId() {
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

    public Record() {
    }

    public Record(UUID id, ZonedDateTime date, int likes, String link) {
        this.id = id;
        this.date = date;
        this.likes = likes;
        this.link = link;
    }
}
