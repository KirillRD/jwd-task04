package com.epam.library.entity;

import java.io.Serializable;
import java.util.Objects;

public class Reservation implements Serializable {
    private int id;
    private int instanceID;
    private int readerID;
    private int bookID;
    private String hallID;
    private String date;

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(int instanceID) {
        this.instanceID = instanceID;
    }

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getHallID() {
        return hallID;
    }

    public void setHallID(String hallID) {
        this.hallID = hallID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && instanceID == that.instanceID && readerID == that.readerID && bookID == that.bookID && Objects.equals(hallID, that.hallID) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instanceID, readerID, bookID, hallID, date);
    }

    @Override
    public String toString() {
        return "ReservationInfo{" +
                "id=" + id +
                ", instanceID=" + instanceID +
                ", readerID=" + readerID +
                ", bookID=" + bookID +
                ", hallID='" + hallID + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
