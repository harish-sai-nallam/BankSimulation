package com.banksimulation.model;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class Customer {

    @NotNull
    private int customerId;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$",
            message = "Names can only have lowercase, uppercase alphabets and spaces")
    private String customerName;

    @NotNull
    @Pattern(regexp = "^[0-9]{12}$",
            message = "Aadhaar number must be exactly 12 digits")
    private String aadharNumber;


    @NotNull
    @Size(min = 10, max = 255, message = "Address must be between 10 and 255 characters")
    private String permanentAddress;


    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$",
            message = "State name can only contain alphabets and spaces")
    private String state;


    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$",
            message = "Country name can only contain alphabets and spaces")
    private String country;


    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$",
            message = "City name can only contain alphabets and spaces")
    private String city;


    @NotNull
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email format")
    private String email;


    @NotNull
    @Pattern(regexp = "^[6-9][0-9]{9}$",
            message = "Phone number must be 10 digits starting with 6, 7, 8, or 9")
    private String phoneNumber;


    @NotNull
    @Pattern(regexp = "^(SINGLE|MARRIED|DIVORCED|WIDOWED)$",
            message = "Marital status must be SINGLE, MARRIED, DIVORCED, or WIDOWED")
    private String maritalStatus;


    @NotNull
    @Past(message = "Date of birth must be in the past")
    @Past
    private LocalDate dob;


    @NotNull
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age cannot exceed 120")
    private int age;

    @NotNull
    private LocalDate createdOn;

    private LocalDate modifiedOn;


    @NotNull
    @Pattern(regexp = "^[MF]$", message = "Gender must be M or F")
    private String gender;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$",
            message = "Father's name can only contain alphabets and spaces")
    private String fatherName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$",
            message = "Mother's name can only contain alphabets and spaces")
    private String motherName;



    public Customer( int customerId, String customerName, String aadharNumber,
                    String permanentAddress, String state, String country, String city, String email,
                    String phoneNumber, String maritalStatus, LocalDate dob, int age, LocalDate createdOn,
                    LocalDate modifiedOn, String gender, String fatherName, String motherName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.aadharNumber = aadharNumber;
        this.permanentAddress = permanentAddress;
        this.state = state;
        this.country = country;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.maritalStatus = maritalStatus;
        this.dob = dob;
        this.age = age;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.gender = gender;
        this.fatherName = fatherName;
        this.motherName = motherName;
    }


    public Customer() {}


    public int getCustomerId() {
        return customerId;
    }


    public String getCustomerName() {
        return customerName;
    }

    public String getAadharNumber() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMaritalStatus() {
        return maritalStatus;
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

    public String getGender() {
        return gender;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAadharNumber(String aadharNumber) {
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    public void setGender (String gender) {
        this.gender = gender;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }
}
