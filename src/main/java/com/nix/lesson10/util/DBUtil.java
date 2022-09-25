package com.nix.lesson10.util;

import com.nix.lesson10.config.JDBCConfig;
import lombok.SneakyThrows;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DBUtil {
    private static final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private static Connection connection;

    public DBUtil(){
        connection = JDBCConfig.getInstance();
    }

    @SneakyThrows
    public static void executor(){
        readAndExecute("vehicles.sql");
        readAndExecute("insertValues.sql");
    }

    private static void readAndExecute(String file) throws URISyntaxException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                        Paths.get(Objects.requireNonNull(classLoader.getResource(file)).toURI()).toFile())));
             Statement statement = connection.createStatement()) {
            StringBuilder create = new StringBuilder();
            String reading = null;
            while ((reading = reader.readLine()) != null) {
                create.append(reading);
            }
            statement.executeUpdate(create.toString());
            connection.commit();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
