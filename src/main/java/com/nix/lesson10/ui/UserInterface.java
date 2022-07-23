package com.nix.lesson10.ui;

import com.nix.lesson10.container.SaleContainer;
import com.nix.lesson10.model.Garage;
import com.nix.lesson10.model.GarageImpl;
import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Motorcycle;
import com.nix.lesson10.model.vehicle.Truck;
import com.nix.lesson10.repository.AutoRepository;
import com.nix.lesson10.repository.MotoRepository;
import com.nix.lesson10.repository.TruckRepository;
import com.nix.lesson10.service.AutoService;
import com.nix.lesson10.service.MotoService;
import com.nix.lesson10.service.TruckService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UserInterface {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final MotoService MOTO_SERVICE = new MotoService(new MotoRepository());
    private static final TruckService TRUCK_SERVICE = new TruckService(new TruckRepository());
    private static final Garage<Auto> GARAGE = new GarageImpl<>();
    private static Integer COUNTER = 1;

    public void start() {
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("\n\n\n*************** Auto factory and store ***************\n\n\n");
            while (true) {
                menu();
                realisation(bf);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private void menu() {
        System.out.println();
        System.out.println("--------------------<Choose item>--------------------");
        System.out.println("1) Auto;");
        System.out.println("2) Motorcycle;");
        System.out.println("3) Truck;");
        System.out.println("4) Sale container;");
        System.out.println("5) Garage;");
        System.out.println("0) Exit!");
        System.out.println("-------------------------------------------------------");
        System.out.println();
    }

    private void realisation(BufferedReader reader) throws IOException {
        String choice = reader.readLine();
        switch (choice) {
            case "1" -> autoImpl(reader);
            case "2" -> motoImpl(reader);
            case "3" -> truckImpl(reader);
            case "4" -> containerImpl(reader);
            case "5" -> garageImpl(reader);
            case "0" -> System.exit(0);
            default -> System.out.println("Wrong number!");
        }
    }

    private void vehicleAuto() {
        System.out.println("--------------------<Choose task>--------------------");
        System.out.println("1) Create a lot of autos;");
        System.out.println("2) Create one auto;");
        System.out.println("3) See all;");
        System.out.println("4) Update;");
        System.out.println("5) Sale on auto;");
        System.out.println("6) See body type of choose car;");
        System.out.println("7) Compare and print;");
        System.out.println("8) Delete;");
        System.out.println("0) Back!");
        System.out.println("-------------------------------------------------------");
    }

    private void vehicleMoto() {
        System.out.println("--------------------<Choose task>--------------------");
        System.out.println("1) Create a lot of motorcycle;");
        System.out.println("2) Create one motorcycle;");
        System.out.println("3) See all;");
        System.out.println("4) Update;");
        System.out.println("5) Get moto by price");
        System.out.println("6) Delete;");
        System.out.println("0) Back!");
        System.out.println("-------------------------------------------------------");
    }

    private void vehicleTruck() {
        System.out.println("--------------------<Choose task>--------------------");
        System.out.println("1) Create a lot of trucks;");
        System.out.println("2) Create one truck;");
        System.out.println("3) See all;");
        System.out.println("4) Update;");
        System.out.println("5) Pick a truck by capacity;");
        System.out.println("6) Delete;");
        System.out.println("0) Back!");
        System.out.println("-------------------------------------------------------");
    }

    private void container() {
        System.out.println("--------------------<Choose task>--------------------");
        System.out.println("1) Sale on auto;");
        System.out.println("2) Sale on motorcycle;");
        System.out.println("3) Sale on truck;");
        System.out.println("0) Back!");
        System.out.println("-------------------------------------------------------");
    }

    private void garage() {
        System.out.println("--------------------<Choose task>--------------------");
        System.out.println("1) Add first vehicle;");
        System.out.println("2) Add last vehicle;");
        System.out.println("3) Get first restyle;");
        System.out.println("4) Get last restyle;");
        System.out.println("5) Get auto by restyle;");
        System.out.println("6) Delete auto by restyle;");
        System.out.println("7) Replace auto by restyle;");
        System.out.println("8) Get size of garage;");
        System.out.println("9) Print all garage;");
        System.out.println("0) Back!");
        System.out.println("-------------------------------------------------------");
    }

    private void garageImpl(BufferedReader reader) throws IOException {
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

    private void autoImpl(BufferedReader reader) throws IOException {
        String choice = " ";
        while (!choice.equals("0")) {
            vehicleAuto();
            choice = reader.readLine();
            switch (choice) {
                case "1" -> addAutos(reader);
                case "2" -> addAuto(reader);
                case "3" -> AUTO_SERVICE.printAll();
                case "4" -> AUTO_SERVICE.update(reader);
                case "5" -> AUTO_SERVICE.saleOnAuto(reader);
                case "6" -> AUTO_SERVICE.getBodyType(reader);
                case "7" -> AUTO_SERVICE.compare();
                case "8" -> AUTO_SERVICE.delete(reader);
                case "0" -> System.out.println("Exit!");
                default -> System.out.println("Wrong number!");
            }
        }
    }

    private void containerImpl(BufferedReader reader) throws IOException {
        String choice = " ";
        while (!choice.equals("0")) {
            container();
            choice = reader.readLine();
            switch (choice) {
                case "1" -> {
                    SaleContainer<Auto> cont = new SaleContainer<>(AUTO_SERVICE.create(reader));
                    cont.getSaleOnAuto();
                    cont.printVehicle();
                    cont.increasePrice(100);
                    cont.printVehicle();
                }
                case "2" -> {
                    SaleContainer<Motorcycle> cont = new SaleContainer<>(MOTO_SERVICE.create(reader));
                    cont.getSaleOnAuto();
                    cont.printVehicle();
                    cont.increasePrice(110.8);
                    cont.printVehicle();
                }
                case "3" -> {
                    SaleContainer<Truck> cont = new SaleContainer<>(TRUCK_SERVICE.create(reader));
                    cont.getSaleOnAuto();
                    cont.printVehicle();
                    cont.increasePrice(80.9);
                    cont.printVehicle();
                }
                case "0" -> System.out.println("Exit!");
                default -> System.out.println("Wrong number!");
            }
        }
    }

    private void motoImpl(BufferedReader reader) throws IOException {
        String choice = " ";
        while (!choice.equals("0")) {
            vehicleMoto();
            choice = reader.readLine();
            switch (choice) {
                case "1" -> addMotos(reader);
                case "2" -> addMoto(reader);
                case "3" -> MOTO_SERVICE.printAll();
                case "4" -> MOTO_SERVICE.update(reader);
                case "5" -> MOTO_SERVICE.getMotoByPrice(reader);
                case "6" -> MOTO_SERVICE.delete(reader);
                case "0" -> System.out.println("Exit!");
                default -> System.out.println("Wrong number!");
            }
        }
    }

    private void truckImpl(BufferedReader reader) throws IOException {
        String choice = " ";
        while (!choice.equals("0")) {
            vehicleTruck();
            choice = reader.readLine();
            switch (choice) {
                case "1" -> addTrucks(reader);
                case "2" -> addTruck(reader);
                case "3" -> TRUCK_SERVICE.printAll();
                case "4" -> TRUCK_SERVICE.update(reader);
                case "5" -> TRUCK_SERVICE.pickTruckByCapacity(reader);
                case "6" -> TRUCK_SERVICE.delete(reader);
                case "0" -> System.out.println("Exit!");
                default -> System.out.println("Wrong number!");
            }
        }
    }

    private static void addAuto(BufferedReader reader) {
        try {
            Auto auto = AUTO_SERVICE.create(reader);
            AUTO_SERVICE.save(auto);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void addAutos(BufferedReader reader) throws IOException {
        System.out.print("Input number of autos to produce: ");
        int count = Integer.parseInt(reader.readLine());
        List<Auto> autos = AUTO_SERVICE.createList(count);
        AUTO_SERVICE.saveList(autos);
    }

    private static void addMoto(BufferedReader reader) {
        try {
            Motorcycle motorcycle = MOTO_SERVICE.create(reader);
            MOTO_SERVICE.save(motorcycle);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void addMotos(BufferedReader reader) throws IOException {
        System.out.print("Input number of motorcycles to produce: ");
        int count = Integer.parseInt(reader.readLine());
        List<Motorcycle> motos = MOTO_SERVICE.createList(count);
        MOTO_SERVICE.saveList(motos);
    }

    private static void addTruck(BufferedReader reader) {
        try {
            Truck truck = TRUCK_SERVICE.create(reader);
            TRUCK_SERVICE.save(truck);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private static void addTrucks(BufferedReader reader) throws IOException {
        System.out.print("Input number of trucks to produce: ");
        int count = Integer.parseInt(reader.readLine());
        List<Truck> trucks = TRUCK_SERVICE.createList(count);
        TRUCK_SERVICE.saveList(trucks);
    }

}
