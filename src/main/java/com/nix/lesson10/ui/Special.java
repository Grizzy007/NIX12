package com.nix.lesson10.ui;

import com.nix.lesson10.model.BinaryTree;
import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.service.AutoService;
import com.nix.lesson10.service.MotoService;
import com.nix.lesson10.service.TruckService;

import java.io.BufferedReader;
import java.io.IOException;

public class Special implements Command {
    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final MotoService MOTO_SERVICE = MotoService.getInstance();
    private static final TruckService TRUCK_SERVICE = TruckService.getInstance();
    private static final BinaryTree<Auto> TREE = new BinaryTree<>();

    @Override
    public void execute(BufferedReader reader) throws IOException {
        int choice;
        do {
            System.out.println("""
                    Interact with:
                    1) Auto;
                    2) Motorcycle;
                    3) Truck;
                    4) Binary tree;
                    5) Exit!
                    """);
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1 -> auto(reader);
                case 2 -> moto(reader);
                case 3 -> truck(reader);
                case 4 -> tree(reader);
                case 5 -> System.out.println("Exit!");
                default -> System.out.println("Incorrect number!");
            }
        } while (choice != 0);
    }

    public void auto(BufferedReader reader) throws IOException {
        int choice;
        do {
            System.out.println("""
                    Actions:
                    1) Compare and print;
                    2) Get sale;
                    3) Get body type;
                    4) Create own auto;
                    5) Exit!
                    """);
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1 -> AUTO_SERVICE.compare();
                case 2 -> AUTO_SERVICE.saleOnAuto(reader);
                case 3 -> AUTO_SERVICE.getBodyType(reader);
                case 4 -> AUTO_SERVICE.save(AUTO_SERVICE.create(reader));
                case 5 -> System.out.println("Exit!");
                default -> System.out.println("Incorrect number!");
            }
        } while (choice != 0);
    }

    public void moto(BufferedReader reader) throws IOException {
        int choice;
        do {
            System.out.println("""
                    Actions:
                    1) Compare and print;
                    2) Get motorcycle by price;
                    3) Create own motorcycle;
                    4) Exit!
                    """);
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1 -> MOTO_SERVICE.compare();
                case 2 -> MOTO_SERVICE.getMotoByPrice(reader);
                case 3 -> MOTO_SERVICE.save(MOTO_SERVICE.create(reader));
                case 4 -> System.out.println("Exit!");
                default -> System.out.println("Incorrect number!");
            }
        } while (choice != 0);
    }

    public void truck(BufferedReader reader) throws IOException {
        int choice;
        do {
            System.out.println("""
                    Actions:
                    1) Compare and print;
                    2) Pick truck by capacity;
                    3) Create own auto;
                    4) Exit!
                    """);
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1 -> TRUCK_SERVICE.compare();
                case 2 -> TRUCK_SERVICE.pickTruckByCapacity(reader);
                case 3 -> TRUCK_SERVICE.save(TRUCK_SERVICE.create(reader));
                case 4 -> System.out.println("Exit!");
                default -> System.out.println("Incorrect number!");
            }
        } while (choice != 0);
    }

    public void tree(BufferedReader reader) throws IOException {
        int choice;
        do {
            System.out.println("""
                    Actions:
                    1) Add an auto to tree;
                    2) Sum of left branch of a tree;
                    3) Sum of right branch of a tree;
                    4) Print vehicles in a tree;
                    0) Exit!
                    """);
            choice = Integer.parseInt(reader.readLine());
            switch (choice) {
                case 1 -> TREE.add(AUTO_SERVICE.create(reader));
                case 2 -> System.out.printf("Sum: %d%n",TREE.leftBranchSum());
                case 3 -> System.out.printf("Sum: %d%n",TREE.rightBranchSum());
                case 4 -> TREE.print();
                case 0 -> System.out.println("Exit!");
                default -> System.out.println("Incorrect number!");
            }
        } while (choice != 0);
    }


}
