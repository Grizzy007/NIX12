package com.nix.lesson10.repository.db;

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
    public static final Connection connection = JDBCConfig.getInstance();

    private DBUtil(){}

    @SneakyThrows
    public static void executor(){
        readAndExecute("auto.xml");
        readAndExecute("insertValues.sql");
    }

    private static void readAndExecute(String file) throws URISyntaxException {
        //File readFrom = Paths.get(Objects.requireNonNull(classLoader.getResource(file)).toURI()).toFile();//new File("./src/main/resources/vehicles.sql");
        String  s = String.valueOf(classLoader.getResourceAsStream(file));
        System.out.println(classLoader.getResource(file));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(s)));
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
