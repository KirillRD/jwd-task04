package com.epam.library.entity.user;

import com.epam.library.entity.user.constant.Gender;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Reader implements Serializable {
    private int id;
    private String nickname;
    private String email;
    private String lastName;
    private String firstName;
    private String fatherName;
    private Date birthday;
    private Gender gender;
    private String passport;
    private String address;
    private String phone;
    private String imageURL;
    private int countDebts;
    private int countDaysDebts;
    private Date minDateReservation;
    private boolean reservationDebts;
    private int countReservation;
    private int countReservationReady;
    private int countReadingHallBooks;
    private int countRental;
    private int countDaysRental;
    private boolean lock;

    public Reader() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getCountDebts() {
        return countDebts;
    }

    public void setCountDebts(int countDebts) {
        this.countDebts = countDebts;
    }

    public int getCountDaysDebts() {
        return countDaysDebts;
    }

    public void setCountDaysDebts(int countDaysDebts) {
        this.countDaysDebts = countDaysDebts;
    }

    public Date getMinDateReservation() {
        return minDateReservation;
    }

    public void setMinDateReservation(Date minDateReservation) {
        this.minDateReservation = minDateReservation;
    }

    public boolean isReservationDebts() {
        return reservationDebts;
    }

    public void setReservationDebts(boolean reservationDebts) {
        this.reservationDebts = reservationDebts;
    }

    public int getCountReservation() {
        return countReservation;
    }

    public void setCountReservation(int countReservation) {
        this.countReservation = countReservation;
    }

    public int getCountReservationReady() {
        return countReservationReady;
    }

    public void setCountReservationReady(int countReservationReady) {
        this.countReservationReady = countReservationReady;
    }

    public int getCountReadingHallBooks() {
        return countReadingHallBooks;
    }

    public void setCountReadingHallBooks(int countReadingHallBooks) {
        this.countReadingHallBooks = countReadingHallBooks;
    }

    public int getCountRental() {
        return countRental;
    }

    public void setCountRental(int countRental) {
        this.countRental = countRental;
    }

    public int getCountDaysRental() {
        return countDaysRental;
    }

    public void setCountDaysRental(int countDaysRental) {
        this.countDaysRental = countDaysRental;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return id == reader.id && countDebts == reader.countDebts && countDaysDebts == reader.countDaysDebts && reservationDebts == reader.reservationDebts && countReservation == reader.countReservation && countReservationReady == reader.countReservationReady && countReadingHallBooks == reader.countReadingHallBooks && countRental == reader.countRental && countDaysRental == reader.countDaysRental && lock == reader.lock && Objects.equals(nickname, reader.nickname) && Objects.equals(email, reader.email) && Objects.equals(lastName, reader.lastName) && Objects.equals(firstName, reader.firstName) && Objects.equals(fatherName, reader.fatherName) && Objects.equals(birthday, reader.birthday) && gender == reader.gender && Objects.equals(passport, reader.passport) && Objects.equals(address, reader.address) && Objects.equals(phone, reader.phone) && Objects.equals(imageURL, reader.imageURL) && Objects.equals(minDateReservation, reader.minDateReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, lastName, firstName, fatherName, birthday, gender, passport, address, phone, imageURL, countDebts, countDaysDebts, minDateReservation, reservationDebts, countReservation, countReservationReady, countReadingHallBooks, countRental, countDaysRental, lock);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", countDebts=" + countDebts +
                ", countDaysDebts=" + countDaysDebts +
                ", minDateReservation=" + minDateReservation +
                ", reservationDebts=" + reservationDebts +
                ", countReservation=" + countReservation +
                ", countReservationReady=" + countReservationReady +
                ", countReadingHallBooks=" + countReadingHallBooks +
                ", countRental=" + countRental +
                ", countDaysRental=" + countDaysRental +
                ", lock=" + lock +
                '}';
    }
}
