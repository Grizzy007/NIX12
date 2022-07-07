package com.nix.lesson10.model;

import java.math.BigDecimal;

public class Motorcycle extends Vehicle {
    private int landing;

    public Motorcycle(String model, BigDecimal price, Brand manufacturer, int landing) {
        super(model, price, manufacturer);
        this.landing = landing;
    }

    public int getLanding() {
        return landing;
    }

    public void setLanding(int landing) {
        this.landing = landing;
    }

    @Override
    public String toString() {
        return manufacturer + " " + model +
                ", landing = " + landing +
                " cm, price = " + price + "$";
    }
}
