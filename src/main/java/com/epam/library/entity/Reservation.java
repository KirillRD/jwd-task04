package com.epam.library.entity;

import com.epam.library.constant.ReservationStatus;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Reservation implements Serializable {
    private int id;
    private int instanceID;
    private int readerID;
    private Date date;
    private ReservationStatus status;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && instanceID == that.instanceID && readerID == that.readerID && Objects.equals(date, that.date) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instanceID, readerID, date, status);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", instanceID=" + instanceID +
                ", readerID=" + readerID +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
