package com.nix.lesson10.model.vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Auto extends Vehicle {
    private Type bodyType;
    private List<String> details;
    private String[] detailNames = {"clutch", "break", "exhaust pipe", "battery", "oil filter", "air filter"
            , "belt", "chain", "transmission", "starter"};

    public Auto(){
        details = new ArrayList<>();
        randomDetail();
        randomDetail();
    }

    private Auto(String model, BigDecimal price, Brand manufacturer, Type bodyType, double volume, int valves) {
        super(model, price, manufacturer, volume, valves);
        this.bodyType = bodyType;
        details = new ArrayList<>();
        randomDetail();
        randomDetail();
    }

    private Auto(String model, BigDecimal price, char currency, Brand manufacturer, LocalDateTime created,
                double volume, int valves, Type bodyType) {
        super(model, price, currency, manufacturer, created, volume, valves);
        this.bodyType = bodyType;
        details = new ArrayList<>();
        randomDetail();
        randomDetail();
    }

    public Type getBodyType() {
        return bodyType;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setBodyType(Type bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public String toString() {
        return manufacturer + " " + model +
                ", bodyType = " + bodyType +
                ", price = " + price + currency
                + ", details: = " + details.stream().reduce((o1, o2) -> o1 + ", " + o2).orElse("No details")
                + ", created = " + created
                + ", engine: = " + engine;
    }

    private void randomDetail() {
        String detail = detailNames[(int) (Math.random() * 10)];
        details.add(detail);
    }
}
