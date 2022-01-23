package com.epam.library.entity.reservation;

import com.epam.library.constant.ReservationStatus;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class ReaderReservation implements Serializable {
    private int reservationID;
    private int bookID;
    private String bookName;
    private String authors;
    private String instanceNumber;
    private String hallName;
    private Date dateReservation;
    private ReservationStatus status;
    private int countDaysReservation;
    private boolean reservationDebts;

    public ReaderReservation() {}

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(String instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public int getCountDaysReservation() {
        return countDaysReservation;
    }

    public void setCountDaysReservation(int countDaysReservation) {
        this.countDaysReservation = countDaysReservation;
    }

    public boolean isReservationDebts() {
        return reservationDebts;
    }

    public void setReservationDebts(boolean reservationDebts) {
        this.reservationDebts = reservationDebts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReaderReservation that = (ReaderReservation) o;
        return reservationID == that.reservationID && bookID == that.bookID && countDaysReservation == that.countDaysReservation && reservationDebts == that.reservationDebts && Objects.equals(bookName, that.bookName) && Objects.equals(authors, that.authors) && Objects.equals(instanceNumber, that.instanceNumber) && Objects.equals(hallName, that.hallName) && Objects.equals(dateReservation, that.dateReservation) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationID, bookID, bookName, authors, instanceNumber, hallName, dateReservation, status, countDaysReservation, reservationDebts);
    }

    @Override
    public String toString() {
        return "ReaderReservation{" +
                "reservationID=" + reservationID +
                ", bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", authors='" + authors + '\'' +
                ", instanceNumber='" + instanceNumber + '\'' +
                ", hallName='" + hallName + '\'' +
                ", dateReservation=" + dateReservation +
                ", status=" + status +
                ", countDaysReservation=" + countDaysReservation +
                ", reservationDebts=" + reservationDebts +
                '}';
    }
}
