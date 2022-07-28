package com.mygdx.quizgame.DBManagement;

import java.sql.SQLException;

public interface Database {
    public void openDatabase();
    public Result queryDatabase(String sql);
    public void closeDatabase();
    public Result selectYearById(int yearId);
    public Result selectEventByYearId(int yearId);
    public Result selectEventById(int eventId);


}
