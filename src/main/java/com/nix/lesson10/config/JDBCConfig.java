package com.nix.lesson10.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConfig {
    private static final String FULL_URL = "jdbc:mysql://localhost:3306/factory?user=root&password=Agent8998Agent_007";
    private static Connection instance;

    public static Connection getInstance() {
        if (instance == null) {
            try {
                instance = DriverManager.getConnection(FULL_URL);
                instance.setAutoCommit(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }
}
