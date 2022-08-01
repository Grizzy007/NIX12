package com.nix.lesson10.ui;

import com.nix.lesson10.service.AutoService;
import com.nix.lesson10.service.MotoService;
import com.nix.lesson10.service.TruckService;

import java.io.BufferedReader;
import java.io.IOException;

public class Print implements Command{
    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final MotoService MOTO_SERVICE = MotoService.getInstance();
    private static final TruckService TRUCK_SERVICE = TruckService.getInstance();

    @Override
    public void execute(BufferedReader reader) throws IOException {
        System.out.println("""
                Print all available:
                1) Auto;
                2) Motorcycle;
                3) Truck.
                """);
        int choice = Integer.parseInt(reader.readLine());
        switch (choice){
            case 1 -> AUTO_SERVICE.printAll();
            case 2 -> MOTO_SERVICE.printAll();
            case 3 -> TRUCK_SERVICE.printAll();
            default -> System.out.println("Incorrect number!");
        }
    }
}
