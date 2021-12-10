package com.epam.library.entity;

import com.epam.library.entity.reservation.ReservationInstance;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservation implements Serializable {
    private int id;
    private int readerID;
    private Date date;
    private List<ReservationInstance> reservationList = new ArrayList<>();

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<ReservationInstance> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<ReservationInstance> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && readerID == that.readerID && Objects.equals(date, that.date) && Objects.equals(reservationList, that.reservationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, readerID, date, reservationList);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", readerID=" + readerID +
                ", date=" + date +
                ", reservationList=" + reservationList +
                '}';
    }
}
