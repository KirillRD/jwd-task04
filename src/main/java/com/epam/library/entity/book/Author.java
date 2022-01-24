package com.epam.library.entity.book;

import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
    private int id;
    private String lastName;
    private String firstName;
    private String fatherName;
    private boolean authorIsUsed;

    public Author() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public boolean isAuthorIsUsed() {
        return authorIsUsed;
    }

    public void setAuthorIsUsed(boolean authorIsUsed) {
        this.authorIsUsed = authorIsUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && authorIsUsed == author.authorIsUsed && Objects.equals(lastName, author.lastName) && Objects.equals(firstName, author.firstName) && Objects.equals(fatherName, author.fatherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, fatherName, authorIsUsed);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", authorIsUsed=" + authorIsUsed +
                '}';
    }
}
