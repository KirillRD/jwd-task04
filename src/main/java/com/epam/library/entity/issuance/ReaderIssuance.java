package com.epam.library.entity.issuance;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Entity of issued book of the reader
 */
public class ReaderIssuance implements Serializable {
    @Serial
    private static final long serialVersionUID = -2851501480268856139L;

    private int issuanceID;
    private int bookID;
    private String bookName;
    private String authors;
    private String instanceNumber;
    private String hallName;
    private double bookPrice;
    private Date dateIssue;
    private Date dateReturn;
    private Date dateReturnPlanned;
    private int countDaysDebts;
    private boolean lost;
    private int countDaysRental;
    private double rentalPrice;

    public ReaderIssuance() {}

    public int getIssuanceID() {
        return issuanceID;
    }

    public void setIssuanceID(int issuanceID) {
        this.issuanceID = issuanceID;
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

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Date getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(Date dateIssue) {
        this.dateIssue = dateIssue;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public Date getDateReturnPlanned() {
        return dateReturnPlanned;
    }

    public void setDateReturnPlanned(Date dateReturnPlanned) {
        this.dateReturnPlanned = dateReturnPlanned;
    }

    public int getCountDaysDebts() {
        return countDaysDebts;
    }

    public void setCountDaysDebts(int countDaysDebts) {
        this.countDaysDebts = countDaysDebts;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public int getCountDaysRental() {
        return countDaysRental;
    }

    public void setCountDaysRental(int countDaysRental) {
        this.countDaysRental = countDaysRental;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReaderIssuance that = (ReaderIssuance) o;
        return issuanceID == that.issuanceID && bookID == that.bookID && Double.compare(that.bookPrice, bookPrice) == 0 && countDaysDebts == that.countDaysDebts && lost == that.lost && countDaysRental == that.countDaysRental && Double.compare(that.rentalPrice, rentalPrice) == 0 && Objects.equals(bookName, that.bookName) && Objects.equals(authors, that.authors) && Objects.equals(instanceNumber, that.instanceNumber) && Objects.equals(hallName, that.hallName) && Objects.equals(dateIssue, that.dateIssue) && Objects.equals(dateReturn, that.dateReturn) && Objects.equals(dateReturnPlanned, that.dateReturnPlanned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issuanceID, bookID, bookName, authors, instanceNumber, hallName, bookPrice, dateIssue, dateReturn, dateReturnPlanned, countDaysDebts, lost, countDaysRental, rentalPrice);
    }

    @Override
    public String toString() {
        return "ReaderIssuance{" +
                "issuanceID=" + issuanceID +
                ", bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", authors='" + authors + '\'' +
                ", instanceNumber='" + instanceNumber + '\'' +
                ", hallName='" + hallName + '\'' +
                ", bookPrice=" + bookPrice +
                ", dateIssue=" + dateIssue +
                ", dateReturn=" + dateReturn +
                ", dateReturnPlanned=" + dateReturnPlanned +
                ", countDaysDebts=" + countDaysDebts +
                ", lost=" + lost +
                ", countDaysRental=" + countDaysRental +
                ", rentalPrice=" + rentalPrice +
                '}';
    }
}
