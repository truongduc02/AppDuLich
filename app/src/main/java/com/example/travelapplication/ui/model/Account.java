package com.example.travelapplication.ui.model;

import java.util.Date;

public class Account {
    private int account_id;
    private int user_id;
    private int role_id;
    private int rank_id;
    private String email;
    private String password;
    private int score;
    private Date created_date;
    private Date updated_date;

    public Account(int account_id, int user_id, int role_id, int rank_id, String email, String password, int score, Date created_date, Date updated_date) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.role_id = role_id;
        this.rank_id = rank_id;
        this.email = email;
        this.password = password;
        this.score = score;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }
    public Account(String email,String password)
    {
        this.email=email;
        this.password=password;
    }
    public Account(){

    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getRank_id() {
        return rank_id;
    }

    public void setRank_id(int rank_id) {
        this.rank_id = rank_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
