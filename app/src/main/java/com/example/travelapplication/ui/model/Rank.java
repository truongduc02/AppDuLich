package com.example.travelapplication.ui.model;

public class Rank {
    private int rank_id;
    private String title;

    private int score;

    public Rank(int rank_id, String title,int score)
    {
        this.rank_id=rank_id;
        this.title=title;
    }

    public int getRank_id() {
        return rank_id;
    }

    public void setRank_id(int rank_id) {
        this.rank_id = rank_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
