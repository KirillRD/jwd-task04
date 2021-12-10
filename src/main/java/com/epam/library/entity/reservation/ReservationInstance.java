package com.epam.library.entity.reservation;

import java.io.Serializable;
import java.util.Objects;

public class ReservationInstance implements Serializable {
    private int id;
    private int instanceID;
    private boolean status;

    public ReservationInstance() {}

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationInstance that = (ReservationInstance) o;
        return id == that.id && instanceID == that.instanceID && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instanceID, status);
    }

    @Override
    public String toString() {
        return "ReservationInstance{" +
                "id=" + id +
                ", instanceID=" + instanceID +
                ", status=" + status +
                '}';
    }
}
