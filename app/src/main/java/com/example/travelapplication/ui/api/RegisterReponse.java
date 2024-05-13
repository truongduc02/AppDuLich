package com.example.travelapplication.ui.api;

import com.example.travelapplication.ui.model.Account;
import com.example.travelapplication.ui.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterReponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("account")
    @Expose
    private Account account;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getUserId() {
        return account;
    }
}
