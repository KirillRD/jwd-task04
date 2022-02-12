package com.epam.library.entity.instance;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Entity of book instance
 */
public class BookInstance implements Serializable {
    @Serial
    private static final long serialVersionUID = -4707454776857086021L;

    private int id;
    private String number;
    private int hallID;
    private String hallName;
    private Date receivedDate;
    private Date writeOffDate;
    private String status;
    private boolean instanceIsUsed;

    public BookInstance() {}

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

    public int getHallID() {
        return hallID;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isInstanceIsUsed() {
        return instanceIsUsed;
    }

    public void setInstanceIsUsed(boolean instanceIsUsed) {
        this.instanceIsUsed = instanceIsUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInstance that = (BookInstance) o;
        return id == that.id && hallID == that.hallID && instanceIsUsed == that.instanceIsUsed && Objects.equals(number, that.number) && Objects.equals(hallName, that.hallName) && Objects.equals(receivedDate, that.receivedDate) && Objects.equals(writeOffDate, that.writeOffDate) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, hallID, hallName, receivedDate, writeOffDate, status, instanceIsUsed);
    }

    @Override
    public String toString() {
        return "BookInstance{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", hallID=" + hallID +
                ", hallName='" + hallName + '\'' +
                ", receivedDate=" + receivedDate +
                ", writeOffDate=" + writeOffDate +
                ", status='" + status + '\'' +
                ", instanceIsUsed=" + instanceIsUsed +
                '}';
    }
}
