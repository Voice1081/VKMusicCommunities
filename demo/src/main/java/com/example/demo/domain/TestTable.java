package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "test_table")
public class TestTable {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;

    public TestTable(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public TestTable() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
        return "TestTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
