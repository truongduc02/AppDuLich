package com.example.travelapplication.ui.model;

import java.io.Serializable;
import java.util.List;

public class Tour implements Serializable   {
    private int tour_id;
    private int category_id;
    private String title;
    private float price;
    private String thumbnail;
    private List<String> departure;
    private String scope;
    private String itinerary;
    private String schedule;
    private String vehicles;
    private String start;
    private  boolean isAddToCart;
    private String description;

    public Tour(int tour_id, int category_id, String title, float price, String thumbnail, List<String> departure, String scope, String itinerary, String schedule, String vehicles, String start) {
        this.tour_id = tour_id;
        this.category_id = category_id;
        this.title = title;
        this.price = price;
        this.thumbnail = thumbnail;
        this.departure = departure;
        this.scope = scope;
        this.itinerary = itinerary;
        this.schedule = schedule;
        this.vehicles = vehicles;
        this.start = start;
    }

    public int getTour_id() {
        return tour_id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public List<String> getDeparture() {
        return departure;
    }

    public void setDeparture(List<String> departure) {
        this.departure = departure;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public boolean isAddToCart() {
        return isAddToCart;
    }

    public void setAddToCart(boolean addToCart) {
        isAddToCart = addToCart;
    }
}