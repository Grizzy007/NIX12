package com.nix.lesson10.repository;

import com.nix.lesson10.model.Auto;
import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Truck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class TruckRepositoryTest {

    private TruckRepository target;

    private Truck truck;

    @BeforeEach
    void setUp() {
        target = new TruckRepository();
        truck = createTruck();
        target.create(truck);
    }

    private Truck createTruck() {
        return new Truck("Model", BigDecimal.ZERO, Brand.VOLKSWAGEN, 15);
    }

    @Test
    void getAll() {
        final List<Truck> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void getById_find() {
        final Optional<Truck> optional = target.getById(truck.getId());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(truck.getId(), optional.get().getId());
    }

    @Test
    void getByIdNoFind() {
        final Optional<Truck> optional = target.getById("123");
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void create() {
        final Truck createTruck = new Truck("Model", BigDecimal.ONE, Brand.VOLKSWAGEN, 20);
        Truck truck = target.create(createTruck);
        Assertions.assertEquals(createTruck, truck);
    }

    @Test
    void updateNotFoundUpdateRandom() {
        final Truck otherTruck = createTruck();
        final boolean actual =
                target.update(otherTruck);
        Assertions.assertTrue(actual);
    }

    @Test
    void update() {
        final Truck t = target.getById(truck.getId()).get();
        t.setBrand(Brand.BMW);
        final boolean actual = target.update(t);
        Assertions.assertTrue(actual);
    }


    @Test
    void delete() {
        final Truck toDelete = new Truck("Model", BigDecimal.ONE, Brand.AUDI, 80);
        target.create(toDelete);
        Truck deleted = target.delete(toDelete.getId());
        Assertions.assertEquals(toDelete, deleted);
    }
}