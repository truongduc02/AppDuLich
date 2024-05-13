package com.example.travelapplication.ui.model;

import java.util.List;

public class Category {

    private int categoryId;
    private String nameCategory;
    private List<Tour> tours;
    private List<Article> articles;

    public Category(int categoryId ,String nameCategory, List<Tour> tours,List<Article> articles) {
        this.categoryId = categoryId;
        this.nameCategory = nameCategory;
        this.tours = tours;
        this.articles = articles;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
