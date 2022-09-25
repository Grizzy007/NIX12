package com.nix.lesson10;

import com.nix.lesson10.ui.UserInterface;
import com.nix.lesson10.util.DBUtil;
import lombok.val;
import org.flywaydb.core.Flyway;

import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
//        Flyway flyway = Flyway.configure()
//                .dataSource("jdbc:mysql://us-cdbr-east-06.cleardb.net/heroku_798da66bf65e891?reconnect=true",
//                        "baa726347944ec", "bf64f7ff")
//                .baselineOnMigrate(true)
//                .locations("db/migration")
//                .load();
//        flyway.migrate();
        UserInterface ui = new UserInterface();
        ui.start();
    }

}