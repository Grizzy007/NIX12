package com.nix.lesson10.model;

import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class GarageImplTest {
    Garage<Auto> garage;
    Auto auto;

    private Auto createAuto() {
        return new Auto("Model", BigDecimal.ZERO, Brand.TOYOTA, Type.SUV, 3, 4);
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
        Auto a = new Auto("M5", BigDecimal.valueOf(1500), Brand.BMW, Type.SEDAN, 4, 6);
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
        garage.addFirst(new Auto("A7", BigDecimal.valueOf(1500), Brand.AUDI, Type.SEDAN, 5.5, 8), 1);
        Auto a = garage.addLast(auto, 5);
        Assertions.assertEquals(garage.getLastRestyle(), auto);
    }
}