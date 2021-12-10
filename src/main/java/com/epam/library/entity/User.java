package com.epam.library.entity;

import com.epam.library.entity.user.Gender;
import com.epam.library.entity.user.Role;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private List<Role> roles = new ArrayList<>();
    private String login;
    private String email;
    private String lastName;
    private String firstName;
    private String fatherName;
    private Date birthdate;
    private Gender gender;
    private String passport;
    private String address;
    private String phone;
    private Date registrationDate;
    private int rating;
    private String imageURL;

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && rating == user.rating && Objects.equals(roles, user.roles) && Objects.equals(login, user.login) && Objects.equals(email, user.email) && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(fatherName, user.fatherName) && Objects.equals(birthdate, user.birthdate) && gender == user.gender && Objects.equals(passport, user.passport) && Objects.equals(address, user.address) && Objects.equals(phone, user.phone) && Objects.equals(registrationDate, user.registrationDate) && Objects.equals(imageURL, user.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roles, login, email, lastName, firstName, fatherName, birthdate, gender, passport, address, phone, registrationDate, rating, imageURL);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roles=" + roles +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", registrationDate=" + registrationDate +
                ", rating=" + rating +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
