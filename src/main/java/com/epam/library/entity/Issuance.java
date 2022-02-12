package com.epam.library.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity of issuance
 */
public class Issuance implements Serializable {
    @Serial
    private static final long serialVersionUID = -1150026367844884222L;

    private int id;
    private int instanceID;
    private int readerID;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issuance issuance = (Issuance) o;
        return id == issuance.id && instanceID == issuance.instanceID && readerID == issuance.readerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instanceID, readerID);
    }

    @Override
    public String toString() {
        return "Issuance{" +
                "id=" + id +
                ", instanceID=" + instanceID +
                ", readerID=" + readerID +
                '}';
    }
}
