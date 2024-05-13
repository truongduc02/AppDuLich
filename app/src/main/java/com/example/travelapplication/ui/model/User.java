package com.example.travelapplication.ui.model;

import org.w3c.dom.Text;

import java.util.Date;

public class User {
    private int user_id;
    private String avatar;
    private String name;
    private String email;
    private String phone_number;
    private Date date_of_birth;
    private String gender;
    private String address;
    private Date created_date;
    private Date updated_date;

    public User(int user_id, String avatar, String name, String email, String phone_number, Date date_of_birth, String gender, String address, Date created_date, Date updated_date) {
        this.user_id = user_id;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.address = address;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public User(){

    }

    public User(String email, String name, String phone_number, String address) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }
}
