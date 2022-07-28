package com.mygdx.quizgame;

public class Quiz {
    int year;
    String event;

    public Quiz(int year, String event) {
        this.year = year;
        this.event = event;
    }

    public Quiz (){

    }

    public Quiz(int year) {
        this.year = year;
    }

    public Quiz(String event) {
        this.event = event;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
