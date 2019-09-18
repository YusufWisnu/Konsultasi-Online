package com.example.konsulyuk.Models;

public class TestModel {

    String name, classes, id;
    Long timestamp = -1L;


    public TestModel(String name, String classes, Long timestamp, String id) {
        this.name = name;
        this.classes = classes;
        this.timestamp = timestamp;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
