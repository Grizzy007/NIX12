package com.nix.lesson10.ui;

import com.nix.lesson10.repository.VehicleFileReader;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements Command {
    private static final VehicleFileReader READER = new VehicleFileReader();

    @Override
    public void execute(BufferedReader reader) throws IOException {
        System.out.println("""
                Read auto from:
                1) Xml;
                2) Json;
                """);
        int choice = Integer.parseInt(reader.readLine());
        switch (choice) {
            case 1 -> System.out.println(READER.redaFromFile("xml"));
            case 2 -> System.out.println(READER.redaFromFile("json"));
        }
    }
}
