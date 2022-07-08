package com.nix.lesson10.model;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Vehicle {
    protected final String id;
    protected String model;
    protected BigDecimal price;
    protected Brand manufacturer;

    protected Vehicle(String model, BigDecimal price, Brand manufacturer) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Brand getBrand() {
        return manufacturer;
    }

    public void setBrand(Brand manufacturer) {
        this.manufacturer = manufacturer;
    }
}
