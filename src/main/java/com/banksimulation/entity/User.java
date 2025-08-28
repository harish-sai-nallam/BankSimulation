package com.banksimulation.entity;

public class User {

    private int userid;
    private String name;
    private int age;
    private String email;
    private String gender;
    private String address;

    public User() {
        // No-arg constructor
    }

    public User(String name, String email, int age, String gender, String address) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }
}
