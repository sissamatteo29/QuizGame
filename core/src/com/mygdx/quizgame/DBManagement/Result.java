package com.mygdx.quizgame.DBManagement;

public interface Result {
    public String getString(int columnIndex);
    public String getString(String columnLable);
    public int getInt(int columnIndex);
    public int getInt(String columnLable);
    public boolean moveToNext();

}
