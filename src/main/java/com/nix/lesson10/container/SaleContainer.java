package com.nix.lesson10.container;

import com.nix.lesson10.model.vehicle.Vehicle;

import java.math.BigDecimal;
import java.util.Random;

public class SaleContainer<T extends Vehicle> {
    private final Random RANDOM = new Random();
    T vehicle;

    public SaleContainer(T vehicle) {
        this.vehicle = vehicle;
    }

    public void getSaleOnAuto() {
        vehicle.setPrice(vehicle.getPrice().subtract(vehicle.getPrice().multiply(BigDecimal.valueOf(0.1 + RANDOM.nextDouble(0.2)))));
    }

    public <V extends Number> void increasePrice(V increase) {
        if (increase.getClass().equals(Integer.class)) {
            vehicle.setPrice(vehicle.getPrice().add(BigDecimal.valueOf((Integer) increase)));
        } else {
            vehicle.setPrice(vehicle.getPrice().add(BigDecimal.valueOf((Double) increase)));
        }
    }

    public void printVehicle() {
        System.out.printf("Your vehicle: %s%n", vehicle);
    }
}
