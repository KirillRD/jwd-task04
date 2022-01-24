package com.epam.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class Publisher implements Serializable {
    private int id;
    private String name;
    private String city;
    private boolean publisherIsUsed;

    public Publisher() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isPublisherIsUsed() {
        return publisherIsUsed;
    }

    public void setPublisherIsUsed(boolean publisherIsUsed) {
        this.publisherIsUsed = publisherIsUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return id == publisher.id && publisherIsUsed == publisher.publisherIsUsed && Objects.equals(name, publisher.name) && Objects.equals(city, publisher.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, publisherIsUsed);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", publisherIsUsed=" + publisherIsUsed +
                '}';
    }
}
