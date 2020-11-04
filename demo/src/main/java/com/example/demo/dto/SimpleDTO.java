package com.example.demo.dto;

import java.io.Serializable;

public class SimpleDTO implements Serializable {
    private String name;
    private Integer someNumber;
    private Boolean someBool;

    public String getName() {
        return String.format("%s is COCK!", name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleDTO(String name, Integer someNumber, Boolean someBool) {
        this.name = name;
        this.someNumber = someNumber;
        this.someBool = someBool;
    }

    public SimpleDTO() {
    }

    public Integer getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(Integer someNumber) {
        this.someNumber = someNumber;
    }

    public Boolean getSomeBool() {
        return someBool;
    }

    public void setSomeBool(Boolean someBool) {
        this.someBool = someBool;
    }

    public SimpleDTO(String name) {
        this.name = name;
    }
}
