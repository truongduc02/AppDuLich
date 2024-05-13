package com.example.travelapplication.ui.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginReponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("account_id")
    @Expose
    private int account_id;

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("rank_id")
    @Expose
    private int rank_id;

    public int getRank_id() {
        return rank_id;
    }

    public void setRank_id(int rank_id) {
        this.rank_id = rank_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginReponse() {
    }

    public String getToken(){
        return  token;
    }
    public void setToken(String token){
        this.token=token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
