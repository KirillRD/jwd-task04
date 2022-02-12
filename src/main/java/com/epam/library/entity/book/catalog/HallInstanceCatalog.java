package com.epam.library.entity.book.catalog;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity of free instance hall for the book catalog
 */
public class HallInstanceCatalog implements Serializable {
    @Serial
    private static final long serialVersionUID = -484466861926293130L;

    private int id;
    private String hallFreeInstances;

    public HallInstanceCatalog() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHallFreeInstances() {
        return hallFreeInstances;
    }

    public void setHallFreeInstances(String hallFreeInstances) {
        this.hallFreeInstances = hallFreeInstances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HallInstanceCatalog that = (HallInstanceCatalog) o;
        return id == that.id && Objects.equals(hallFreeInstances, that.hallFreeInstances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hallFreeInstances);
    }

    @Override
    public String toString() {
        return "HallInstanceCatalog{" +
                "id=" + id +
                ", hallFreeInstances='" + hallFreeInstances + '\'' +
                '}';
    }
}
