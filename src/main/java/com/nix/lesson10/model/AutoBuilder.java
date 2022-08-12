package com.nix.lesson10.model;

import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Engine;
import com.nix.lesson10.model.vehicle.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AutoBuilder {
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
        auto.setManufacturer(manufacturer);
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
