package com.example.travelapplication.ui.model;

import java.util.Date;
import java.util.List;

public class Bill {
    private int bill_id;
    private int account_id;
    private Date created_date;
    private String name;
    private float discount;
    private float total;
    private int status;
    private String note;
    private List<GioHang> products;

    private String payment_method;

    private String email;

    public List<GioHang> getProducts() {
        return products;
    }

    public Bill(String name, float discount, float total, String note, List<GioHang> products, String email) {
        this.name = name;
        this.discount = discount;
        this.total = total;
        this.note = note;
        this.products = products;
        this.email = email;
    }

    public void setProducts(List<GioHang> products) {
        this.products = products;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bill(int bill_id, int account_id, Date created_date, String name, float discount, float total, int status, String note, String payment_method) {
        this.bill_id = bill_id;
        this.account_id = account_id;
        this.created_date = created_date;
        this.name = name;
        this.discount = discount;
        this.total = total;
        this.status = status;
        this.note = note;
        this.payment_method = payment_method;
    }
    public Bill(){

    }
    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
