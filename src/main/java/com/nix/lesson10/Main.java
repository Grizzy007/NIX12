package com.nix.lesson10;

import com.nix.lesson10.ui.UserInterface;
import org.flywaydb.core.Flyway;


public class Main {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/hibernate_vehicles", "root", "Agent8998Agent_007")
                .baselineOnMigrate(true)
                .locations("db/migration")
                .load();
        flyway.migrate();
        UserInterface ui = new UserInterface();
        ui.start();

    }

}

