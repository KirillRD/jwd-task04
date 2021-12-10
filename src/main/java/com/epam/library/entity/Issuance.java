package com.epam.library.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Issuance implements Serializable {
    private int id;
    private int instanceID;
    private int readerID;
    private Date issueDate;
    private Date returnDate;
    private Date returnPlanedDate;
    private boolean lost;

    public Issuance() {}

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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getReturnPlanedDate() {
        return returnPlanedDate;
    }

    public void setReturnPlanedDate(Date returnPlanedDate) {
        this.returnPlanedDate = returnPlanedDate;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issuance issuance = (Issuance) o;
        return id == issuance.id && instanceID == issuance.instanceID && readerID == issuance.readerID && lost == issuance.lost && Objects.equals(issueDate, issuance.issueDate) && Objects.equals(returnDate, issuance.returnDate) && Objects.equals(returnPlanedDate, issuance.returnPlanedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instanceID, readerID, issueDate, returnDate, returnPlanedDate, lost);
    }

    @Override
    public String toString() {
        return "Issuance{" +
                "id=" + id +
                ", instanceID=" + instanceID +
                ", readerID=" + readerID +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", returnPlanedDate=" + returnPlanedDate +
                ", lost=" + lost +
                '}';
    }
}
