package com.nix.lesson10.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConfig {
    private static final String FULL_URL = "jdbc:mysql://localhost:3306/invoices?user=root&password=Agent8998Agent_007";
    private static final String URL = "jdbc:postgresql://abul.db.elephantsql.com:5432/atfnxgyq";
    private static final String USER = "atfnxgyq";
    private static final String PASSWORD = "6PLhxctEeXcFH0yGFHHb5zSwSzgtv2SG";

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private static Connection instance;

    @SneakyThrows
    public static Connection getInstance() {
        if (instance == null) {
            try {
                instance = DriverManager.getConnection(FULL_URL);
                //instance = DriverManager.getConnection(URL, USER, PASSWORD);
                instance.setAutoCommit(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }
}
