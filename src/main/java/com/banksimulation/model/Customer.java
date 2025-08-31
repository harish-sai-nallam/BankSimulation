package com.banksimulation.model;

import java.time.LocalDate;

public class Customer {

    private String password;
    private int customerId;
    private String customerName;
    private int aadharNumber;
    private String permanentAddress;
    private String state;
    private String country;
    private String city;
    private String email;
    private int phoneNumber;
    private String status;
    private LocalDate dob;
    private int age;
    private LocalDate createdOn;
    private LocalDate modifiedOn;
    private char gender;
    private String fatherName;
    private String motherName;


    public Customer(String password, int customerId, String customerName, int aadharNumber,
                    String permanentAddress, String state, String country, String city, String email,
                    int phoneNumber, String status, LocalDate dob, int age, LocalDate createdOn,
                    LocalDate modifiedOn, char gender, String fatherName, String motherName) {
        this.password = password;
        this.customerId = customerId;
        this.customerName = customerName;
        this.aadharNumber = aadharNumber;
        this.permanentAddress = permanentAddress;
        this.state = state;
        this.country = country;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.dob = dob;
        this.age = age;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.gender = gender;
        this.fatherName = fatherName;
        this.motherName = motherName;
    }


    public Customer() {}


    public String getPassword() {
        return password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getAadharNumber() {
        return aadharNumber;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDob() {
        return dob;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public char getGender() {
        return gender;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAadharNumber(int aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }
}
