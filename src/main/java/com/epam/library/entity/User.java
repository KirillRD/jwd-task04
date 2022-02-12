package com.epam.library.entity;

import com.epam.library.entity.user.constant.Gender;
import com.epam.library.entity.user.constant.Role;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Entity of user
 */
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2210401745041127705L;

    private int id;
    private Role role;
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
    private boolean lock;

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        User user = (User) o;
        return id == user.id && lock == user.lock && role == user.role && Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email) && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(fatherName, user.fatherName) && Objects.equals(birthday, user.birthday) && gender == user.gender && Objects.equals(passport, user.passport) && Objects.equals(address, user.address) && Objects.equals(phone, user.phone) && Objects.equals(imageURL, user.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, nickname, email, lastName, firstName, fatherName, birthday, gender, passport, address, phone, imageURL, lock);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
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
                ", lock=" + lock +
                '}';
    }
}
