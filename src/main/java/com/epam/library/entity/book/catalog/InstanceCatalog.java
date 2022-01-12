package com.epam.library.entity.book.catalog;

import java.io.Serializable;
import java.util.Objects;

public class InstanceCatalog implements Serializable {
    private int id;
    private String number;
    private String hall;

    public InstanceCatalog() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceCatalog that = (InstanceCatalog) o;
        return id == that.id && Objects.equals(number, that.number) && Objects.equals(hall, that.hall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, hall);
    }

    @Override
    public String toString() {
        return "InstanceCatalog{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", hall='" + hall + '\'' +
                '}';
    }
}
