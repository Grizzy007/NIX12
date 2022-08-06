package com.nix.lesson10.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInterface {

    public void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final Instructions[] instructions = Instructions.values();
            List<String> names = getName(instructions);
            Command command;
            do {
                command = executor(instructions, reader);
            } while (command != null);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private Command executor(Instructions[] instructions, BufferedReader reader) throws IOException {
        System.out.println("""
                Choose action:
                1) Create vehicle;
                2) Update vehicle;
                3) Print vehicles;
                4) Special actions;
                5) Restyle garage;
                6) Read  auto from file;
                7) Delete vehicle;
                """);
        System.out.println("Press 7 to exit...");
        int choice = Integer.parseInt(reader.readLine());
        Instructions toExecute = instructions[choice - 1];
        return toExecute.execute(reader);
    }

    private List<String> getName(Instructions[] instructions) {
        List<String> instructionsNames = new ArrayList<>(instructions.length);
        Arrays.stream(instructions).forEach(name -> instructionsNames.add(name.getCommand()));
        return instructionsNames;
    }
}
