package com.epam.library.entity.user;

import com.epam.library.entity.user.constant.Role;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity of entered user data
 */
public class UserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -1444834058102610006L;

    private int id;
    private Role role;
    private String nickname;
    private String email;
    private String lastName;
    private String firstName;
    private String fatherName;
    private String birthday;
    private String gender;
    private String passport;
    private String address;
    private String phone;
    private String imageURL;

    public UserInfo() {}

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id && role == userInfo.role && Objects.equals(nickname, userInfo.nickname) && Objects.equals(email, userInfo.email) && Objects.equals(lastName, userInfo.lastName) && Objects.equals(firstName, userInfo.firstName) && Objects.equals(fatherName, userInfo.fatherName) && Objects.equals(birthday, userInfo.birthday) && Objects.equals(gender, userInfo.gender) && Objects.equals(passport, userInfo.passport) && Objects.equals(address, userInfo.address) && Objects.equals(phone, userInfo.phone) && Objects.equals(imageURL, userInfo.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, nickname, email, lastName, firstName, fatherName, birthday, gender, passport, address, phone, imageURL);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", role=" + role +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
