package com.epam.library.entity.book.dictionary;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity of book type
 */
public class Type implements Serializable {
    @Serial
    private static final long serialVersionUID = -2673027309635699371L;

    private int id;
    private String name;
    private boolean typeIsUsed;

    public Type() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTypeIsUsed() {
        return typeIsUsed;
    }

    public void setTypeIsUsed(boolean typeIsUsed) {
        this.typeIsUsed = typeIsUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return id == type.id && typeIsUsed == type.typeIsUsed && Objects.equals(name, type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeIsUsed);
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeIsUsed=" + typeIsUsed +
                '}';
    }
}
