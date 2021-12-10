package com.epam.library.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Instance implements Serializable {
    private int id;
    private int bookID;
    private String number;
    private int hallID;
    private Date receivedDate;
    private Date writeOffDate;

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

    public int getHallID() {
        return hallID;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getWriteOffDate() {
        return writeOffDate;
    }

    public void setWriteOffDate(Date writeOffDate) {
        this.writeOffDate = writeOffDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return id == instance.id && bookID == instance.bookID && hallID == instance.hallID && Objects.equals(number, instance.number) && Objects.equals(receivedDate, instance.receivedDate) && Objects.equals(writeOffDate, instance.writeOffDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookID, number, hallID, receivedDate, writeOffDate);
    }

    @Override
    public String toString() {
        return "Instance{" +
                "id=" + id +
                ", bookID=" + bookID +
                ", number='" + number + '\'' +
                ", hallID=" + hallID +
                ", receivedDate=" + receivedDate +
                ", writeOffDate=" + writeOffDate +
                '}';
    }
}
