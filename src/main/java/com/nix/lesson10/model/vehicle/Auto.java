package com.nix.lesson10.model.vehicle;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Auto extends Vehicle {
    @Enumerated(value = EnumType.STRING)
    private Type bodyType;
    @Transient
    private List<String> details;
    @Transient
    private String[] detailNames = {"clutch", "break", "exhaust pipe", "battery", "oil filter", "air filter"
            ,"belt", "chain", "transmission", "starter"};

    public Auto(){
        details = new ArrayList<>();
        randomDetail();
        randomDetail();
    }

    public Auto(String model, BigDecimal price, Brand manufacturer, Type bodyType, double volume, int valves) {
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
    public static class AutoBuilder {
        private Auto auto;

        public AutoBuilder() {
            auto = new Auto();
        }

        public void buildModel(String model) {
            auto.setModel(model);
        }

        public void buildPrice(BigDecimal price) {
            auto.setPrice(price);
        }

        public void buildManufacturer(Brand manufacturer) {
            auto.setBrand(manufacturer);
        }

        public void buildCreated(LocalDateTime created) {
            auto.setCreated(created);
        }

        public void buildEngine(Engine engine) {
            auto.setEngine(engine);
        }

        public void buildBodyType(Type bodyType) {
            if (bodyType.toString().length() <= 20) {
                auto.setBodyType(bodyType);
            }
        }

        public Auto getAuto() {
            return auto;
        }
    }
}
