package com.epam.library.entity.user;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class RegistrationInfo implements Serializable {
    private String login;
    private String password;
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

    public RegistrationInfo() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationInfo that = (RegistrationInfo) o;
        return Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(email, that.email) && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(fatherName, that.fatherName) && Objects.equals(birthdate, that.birthdate) && gender == that.gender && Objects.equals(passport, that.passport) && Objects.equals(address, that.address) && Objects.equals(phone, that.phone) && Objects.equals(registrationDate, that.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, email, lastName, firstName, fatherName, birthdate, gender, passport, address, phone, registrationDate);
    }

    @Override
    public String toString() {
        return "RegistrationInfo{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
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
                '}';
    }
}
