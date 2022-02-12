package com.epam.library.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity of review
 */
public class Review implements Serializable {
    @Serial
    private static final long serialVersionUID = -5335482904027907963L;

    private int id;
    private int bookID;
    private int readerID;
    private int rating;
    private String comment;

    public Review() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id && bookID == review.bookID && readerID == review.readerID && rating == review.rating && Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookID, readerID, rating, comment);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", bookID=" + bookID +
                ", readerID=" + readerID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
