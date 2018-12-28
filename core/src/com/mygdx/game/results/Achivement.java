package com.mygdx.game.results;

public class Achivement {

    private String  name;
    private String  description;
    private boolean isComplete;

    private int     crit;

    public Achivement() {

    }

    // геттеры
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public boolean isComplete() {
        return isComplete;
    }

    // сеттеры
    public void setName(String name) {
        this.name = name;
    }
   public void setDescription(String description) {
        this.description = description;
    }
    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
