package com.epam.library.entity.review;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class BookReview implements Serializable {
    private int userID;
    private String nickname;
    private String imageURL;
    private int id;
    private int rating;
    private String comment;
    private Date date;

    public BookReview() {}

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookReview that = (BookReview) o;
        return userID == that.userID && id == that.id && rating == that.rating && Objects.equals(nickname, that.nickname) && Objects.equals(imageURL, that.imageURL) && Objects.equals(comment, that.comment) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, nickname, imageURL, id, rating, comment, date);
    }

    @Override
    public String toString() {
        return "BookReview{" +
                "userID=" + userID +
                ", nickname='" + nickname + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
