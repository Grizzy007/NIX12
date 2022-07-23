package com.nix.lesson10.model.vehicle;

import java.math.BigDecimal;

public class Truck extends Vehicle {
    private int capacity;

    public Truck(String model, BigDecimal price, Brand manufacturer, int capacity) {
        super(model, price, manufacturer);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return manufacturer + " " + model +
                ", capacity = " + capacity +
                ", price = " + price + "$";
    }
}
