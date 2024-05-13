package com.example.travelapplication.ui.model;

import java.io.Serializable;

public class Article implements Serializable {
    private int article_id;
    private int category_id;
    private String title;
    private String thumbnail;
    private String created_date;
    private String author;
    private String description;

    public Article(int article_id, int category_id, String title, String thumbnail, String created_date, String author) {
        this.article_id = article_id;
        this.category_id = category_id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.created_date = created_date;
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
