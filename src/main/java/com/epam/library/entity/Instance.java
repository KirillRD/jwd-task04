package com.epam.library.entity;

import java.io.Serializable;
import java.util.Objects;

public class Instance implements Serializable {
    private int id;
    private int bookID;
    private String number;
    private String hallID;
    private String receivedDate;
    private String writeOffDate;

    public Instance() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHallID() {
        return hallID;
    }

    public void setHallID(String hallID) {
        this.hallID = hallID;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getWriteOffDate() {
        return writeOffDate;
    }

    public void setWriteOffDate(String writeOffDate) {
        this.writeOffDate = writeOffDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance that = (Instance) o;
        return id == that.id && bookID == that.bookID && Objects.equals(number, that.number) && Objects.equals(hallID, that.hallID) && Objects.equals(receivedDate, that.receivedDate) && Objects.equals(writeOffDate, that.writeOffDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookID, number, hallID, receivedDate, writeOffDate);
    }

    @Override
    public String toString() {
        return "InstanceInfo{" +
                "id=" + id +
                ", bookID=" + bookID +
                ", number='" + number + '\'' +
                ", hallID='" + hallID + '\'' +
                ", receivedDate='" + receivedDate + '\'' +
                ", writeOffDate='" + writeOffDate + '\'' +
                '}';
    }
}
