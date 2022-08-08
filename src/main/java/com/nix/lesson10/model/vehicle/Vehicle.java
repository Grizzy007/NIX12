package com.nix.lesson10.model.vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Vehicle {
    protected final String id;
    protected String model;
    protected BigDecimal price;
    protected char currency;
    protected Brand manufacturer;
    protected LocalDateTime created;
    protected Engine engine;

    protected Vehicle(String model, BigDecimal price, Brand manufacturer, double volume, int valves) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
        this.engine = new Engine(volume, manufacturer, valves);
        currency = '$';
        created = LocalDateTime.now().minusHours((long) (Math.random() * 25));
    }

    protected Vehicle(String model, BigDecimal price, char currency, Brand manufacturer, LocalDateTime created,
                      double volume, int valves) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
        this.currency = currency;
        this.manufacturer = manufacturer;
        this.created = created;
        this.engine = new Engine(volume, manufacturer, valves);
    }

    protected Vehicle() {
        this.id = UUID.randomUUID().toString();
    }

    public Brand getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Brand manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
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
