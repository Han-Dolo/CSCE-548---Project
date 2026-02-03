package com.campus.rsvp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL = "jdbc:mysql://localhost:3306/campus_rsvp";
    private static final String USER = "root";
    private static final String PASSWORD = "Ronaldo#7#";

    private ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
