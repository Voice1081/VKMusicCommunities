package com.example.demo.domain;

import com.example.demo.dto.RecordDTO;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre {
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
}
