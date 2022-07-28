package com.mygdx.quizgame.DBManagement;

import com.badlogic.gdx.Gdx;

import java.sql.*;

public class DesktopResult implements Result {
    public ResultSet rs;
    private boolean isEmpty = false;

    //it makes possible to create an instance without parameters
    public DesktopResult() {

    }

    public DesktopResult(ResultSet rs){
        this.rs = rs;
    }

    public void setRs (ResultSet rs){
        this.rs = rs;
    }

    @Override
    public String getString(int columnIndex) {

        if (rs == null){
            Gdx.app.error("DesktopResultError", "DesktopResult internal ResultSet was not initialised!", new NullPointerException());
            throw new NullPointerException();
        }

        try {
            return rs.getString(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;    //In caso di errore
    }

    @Override
    public String getString(String columnLable) {

        if (rs == null){
            Gdx.app.error("DesktopResultError", "DesktopResult internal ResultSet was not initialised!", new NullPointerException());
            throw new NullPointerException();
        }

        try {
            return rs.getString(columnLable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;    //In caso di errore
    }

    @Override
    public int getInt(int columnIndex) {
        if (rs == null){
            Gdx.app.error("DesktopResultError", "DesktopResult internal ResultSet was not initialised!", new NullPointerException());
            throw new NullPointerException();
        }

        try {
            return rs.getInt(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;       //In caso di errore
    }

    @Override
    public int getInt(String columnLable) {

        if (rs == null){
            Gdx.app.error("DesktopResultError", "DesktopResult internal ResultSet was not initialised!", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            return rs.getInt(columnLable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;   //In caso di errore
    }

    @Override
    public boolean moveToNext() {

        if (rs == null){
            Gdx.app.error("DesktopResultError", "DesktopResult internal ResultSet was not initialised!", new NullPointerException());
            throw new NullPointerException();
        }
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;   //In caso di errore
    }
}
