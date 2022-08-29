package com.nix.lesson10.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

public enum Instructions {
    ACTIONS_WITH_DB("Database actions", new DBCommand()),
    CREATE("Create vehicle", new Create()),
    UPDATE("Update vehicle", new Update()),
    PRINT("Print vehicle", new Print()),
    SPECIAL("Special actions", new Special()),
    GARAGE("Auto restyle garage", new RestyleGarage()),
    FILE("Read from file", new ReadFile()),
    DELETE("Delete vehicle", new Delete()),
    EXIT("Exit", null);

    private final String command;
    private final Command action;

    Instructions(String command, Command action) {
        this.command = command;
        this.action = action;
    }

    public String getCommand() {
        return command;
    }

    public Command getAction() {
        return action;
    }

    public Command execute(BufferedReader reader) throws IOException, URISyntaxException {
        if (action != null) {
            action.execute(reader);
        }
        return action;
    }

}
