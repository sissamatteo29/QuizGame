package com.mygdx.quizgame.DBManagement;

import java.sql.*;

public class DesktopDatabase implements Database{

    private Connection c;
    private PreparedStatement statement;
    private Statement genericStatement;


    @Override
    public void openDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            c = DriverManager.getConnection("jdbc:sqlite:QuizDatabase.db");
            System.out.println("Database opened");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result queryDatabase(String sql) {
        try {
            genericStatement = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = genericStatement.executeQuery(sql);
            return new DesktopResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void closeDatabase() {
       if (statement != null) {
           try {
               statement.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

       if (genericStatement != null) {
           try {
               genericStatement.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result selectYearById(int yearId) {
        try {
            statement = c.prepareStatement("SELECT * FROM YEAR WHERE YEAR_ID = ?");
            statement.setInt(1,yearId);
            ResultSet rs = statement.executeQuery();
            /*statement.close();*/
            return new DesktopResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Result selectEventByYearId(int yearId) {
        try {
            statement = c.prepareStatement("SELECT * FROM EVENT WHERE YEAR_ID = ?");
            statement.setInt(1, yearId);
            ResultSet rs = statement.executeQuery();
            /*statement.close();*/
            return new DesktopResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Result selectEventById(int eventId) {
        try {
            statement = c.prepareStatement("SELECT * FROM EVENT WHERE EVENT_ID = ?");
            statement.setInt(1, eventId);
            ResultSet rs = statement.executeQuery();
            /*statement.close();*/
            return new DesktopResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
