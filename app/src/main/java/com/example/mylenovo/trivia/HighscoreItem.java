package com.example.mylenovo.trivia;

public class HighscoreItem {

    String name;
    String score;

    // Constructor
    public HighscoreItem(String name, String score){
        this.name = name;
        this.score = score;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }
}
