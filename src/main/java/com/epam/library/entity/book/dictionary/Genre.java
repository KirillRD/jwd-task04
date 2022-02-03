package com.epam.library.entity.book.dictionary;

import java.io.Serializable;
import java.util.Objects;

public class Genre implements Serializable {
    private int id;
    private String name;
    private boolean genreIsUsed;

    public Genre() {}

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

    public boolean isGenreIsUsed() {
        return genreIsUsed;
    }

    public void setGenreIsUsed(boolean genreIsUsed) {
        this.genreIsUsed = genreIsUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id == genre.id && genreIsUsed == genre.genreIsUsed && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genreIsUsed);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genreIsUsed=" + genreIsUsed +
                '}';
    }
}
