package com.nix.lesson10.model.vehicle;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Motorcycle extends Vehicle {
    private int landing;

    public Motorcycle(String model, BigDecimal price, Brand manufacturer, int landing, double volume, int valves) {
        super(model, price, manufacturer, volume, valves);
        this.landing = landing;
    }

    public Motorcycle() {
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
                " cm, price = " + price + currency
                + ", created = " + created;
    }
}
