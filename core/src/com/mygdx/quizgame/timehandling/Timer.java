package com.mygdx.quizgame.timehandling;

public class Timer {

    int minutes;
    int seconds;
    float timeCount;
    boolean finished;
    boolean isUpdatable;

    public Timer(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        timeCount = 0;
        finished = false;
        isUpdatable = true;
    }

    public Timer(Timer t){
        this.minutes = t.minutes;
        this.seconds = t.seconds;
        timeCount = 0;
        finished = false;
        isUpdatable = true;
    }

    public boolean isUpdatable() {
        return isUpdatable;
    }

    public void setUpdatable(boolean updatable) {
        isUpdatable = updatable;
    }

    public void update(float deltatime){
        if (isUpdatable == false){
            return;
        }
        timeCount += deltatime;
        if (timeCount >= 1){
            //1 second has passed
            if(seconds == 0){
                if (minutes == 0){
                    finished = true;
                } else {
                    //seconds to 0 but there still are minutes available
                    minutes--;
                    seconds = 59;
                }
            } else {
                //seconds available
                seconds--;
            }
            //restart counting from 0
            timeCount = 0;
        }
    }

    public void sub1(){
        if(seconds == 0){
            if (minutes == 0){
                finished = true;
            } else {
                //seconds to 0 but there still are minutes available
                minutes--;
                seconds = 59;
            }
        } else {
            //seconds available
            seconds--;
        }
    }

    public void sub10(){
        for(int i = 0; i < 10; i++){
            this.sub1();
            if (this.finished == true){
                break;
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(minutes).append(':');
        if (seconds < 10){
            sb.append('0').append(seconds);
        } else {
            sb.append(seconds);
        }

        return sb.toString();

    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }



}
