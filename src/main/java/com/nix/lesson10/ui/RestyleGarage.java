package com.nix.lesson10.ui;

import com.nix.lesson10.model.Garage;
import com.nix.lesson10.model.GarageImpl;
import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.service.AutoService;

import java.io.BufferedReader;
import java.io.IOException;

public class RestyleGarage implements Command {
    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final Garage<Auto> GARAGE = new GarageImpl<>();
    private static Integer COUNTER = 0;

    @Override
    public void execute(BufferedReader reader) throws IOException {
        String choice = " ";
        while (!choice.equals("0")) {
            garage();
            choice = reader.readLine();
            switch (choice) {
                case "1" -> System.out.println(GARAGE.addFirst(AUTO_SERVICE.create(reader), COUNTER++));
                case "2" -> System.out.println(GARAGE.addLast(AUTO_SERVICE.create(reader), COUNTER++));
                case "3" -> System.out.println(GARAGE.getFirstRestyle());
                case "4" -> System.out.println(GARAGE.getLastRestyle());
                case "5" -> {
                    System.out.println("Input number of restyle of vehicle: ");
                    System.out.println(GARAGE.autoByRestyle(Integer.parseInt(reader.readLine())));
                }
                case "6" -> {
                    System.out.println("Input number of restyle of vehicle: ");
                    System.out.println(GARAGE.deleteAutoByRestyle(Integer.parseInt(reader.readLine())));
                }
                case "7" -> {
                    System.out.println("Input number of restyle of vehicle: ");
                    System.out.println(GARAGE.replaceAutoByRestyle(Integer.parseInt(reader.readLine()),
                            AUTO_SERVICE.create(reader)));
                }
                case "8" -> System.out.println(GARAGE.size());
                case "9" -> System.out.println(GARAGE);
                case "0" -> System.out.println("Exit!");
                default -> System.out.println("Wrong number!");
            }
        }
    }

    private void garage() {
        System.out.println("""
                --------------------<Choose task>--------------------
                1) Add first vehicle;
                2) Add last vehicle;
                3) Get first restyle;
                4) Get last restyle;
                5) Get auto by restyle;
                6) Delete auto by restyle;
                7) Replace auto by restyle;
                8) Get size of garage;
                9) Print all garage;
                0) Back!
                -------------------------------------------------------
                """);
    }
}

