package com.nix.lesson10.model;

import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Engine;
import com.nix.lesson10.model.vehicle.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class GarageImplTest {
    Garage<Auto> garage;
    Auto auto;

    private Auto createAuto() {
        AutoBuilder builder = new AutoBuilder();
        builder.buildModel("Model");
        builder.buildPrice( BigDecimal.valueOf(1200));
        builder.buildManufacturer(Brand.AUDI);
        builder.buildCreated(LocalDateTime.now());
        builder.buildBodyType(Type.SUV);
        builder.buildEngine(new Engine(4,
                Brand.AUDI,
                4));
        return builder.getAuto();
    }

    @BeforeEach
    void setUp() {
        garage = new GarageImpl<>();
        auto = createAuto();
    }

    @Test
    void addFirst() {
        garage.addFirst(auto, 1);
        Assertions.assertEquals(garage.autoByRestyle(1).get(), auto);
    }

    @Test
    void addLast() {
        Assertions.assertEquals(garage.addLast(auto, 2), auto);
    }

    @Test
    void size() {
        garage.addFirst(auto, 1);
        garage.addFirst(auto, 2);
        garage.addLast(auto, 3);
        Assertions.assertEquals(garage.size(), 3);
    }

    @Test
    void autoByRestyle() {
        garage.addFirst(auto, 1);
        Assertions.assertEquals(garage.autoByRestyle(1).get(), auto);
    }

    @Test
    void deleteAutoByRestyle() {
        garage.addFirst(auto, 1);
        garage.addFirst(auto, 1);
        Assertions.assertEquals(garage.deleteAutoByRestyle(1).get(), auto);
    }

    @Test
    void replaceAutoByRestyle() {
        garage.addFirst(auto, 10);
        AutoBuilder builder = new AutoBuilder();
        builder.buildModel("Model");
        builder.buildPrice( BigDecimal.valueOf(1200));
        builder.buildManufacturer(Brand.AUDI);
        builder.buildCreated(LocalDateTime.now());
        builder.buildBodyType(Type.SUV);
        builder.buildEngine(new Engine(4,
                Brand.AUDI,
                4));
        Auto a = builder.getAuto();
        Assertions.assertEquals(garage.replaceAutoByRestyle(10, a).get(), a);
    }


    @Test
    void getFirstRestyle() {
        Auto a = garage.addFirst(createAuto(), 1);
        garage.addLast(auto, 5);
        Assertions.assertEquals(garage.getFirstRestyle(), a);
    }

    @Test
    void getLastRestyle() {
        garage.addFirst(auto, 1);
        Auto a = garage.addLast(auto, 5);
        Assertions.assertEquals(garage.getLastRestyle(), auto);
    }
}