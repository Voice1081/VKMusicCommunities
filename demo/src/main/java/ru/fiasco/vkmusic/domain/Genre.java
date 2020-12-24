package ru.fiasco.vkmusic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre implements Comparable<Genre>{
    @Id
    @Column(name = "id", columnDefinition = "text")
    private String id;
    @Column(name = "name", columnDefinition = "text")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    @Override
    public int compareTo(Genre o) {
        return this.name.compareTo(o.getName());
    }
}
