package com.example.travelapplication.ui.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GioHang implements Serializable {

    private int cart_id;

    private int tour_id;
    private String title;
    private float price;
    private String thumbnail;
    private float sub_total;
    private int quantity_selected;
    private String departure_selected;

    public GioHang() {
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getTour_id() {
        return tour_id;
    }

    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public float getSub_total() {
        return sub_total;
    }

    public void setSub_total(float sub_total) {
        this.sub_total = sub_total;
    }

    public int getQuantity_selected() {
        return quantity_selected;
    }

    public void setQuantity_selected(int quantity_selected) {
        this.quantity_selected = quantity_selected;
    }

    public String getDeparture_selected() {
        return departure_selected;
    }

    public void setDeparture_selected(String departure_selected) {
        this.departure_selected = departure_selected;
    }
}
