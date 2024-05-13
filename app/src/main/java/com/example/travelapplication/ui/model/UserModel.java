package com.example.travelapplication.ui.model;

import java.util.Date;
import java.util.List;

public class UserModel {
    private int user_id;
    private int tour_id;
    private int quantity;
    private String departure;
    private String token;


    public UserModel(int user_id, int tour_id, int quantity, String departure, String token) {
        this.user_id = user_id;
        this.tour_id = tour_id;
        this.quantity = quantity;
        this.departure = departure;
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTour_id() {
        return tour_id;
    }

    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
