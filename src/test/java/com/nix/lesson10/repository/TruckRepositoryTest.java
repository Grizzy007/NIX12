package com.nix.lesson10.repository;

import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Truck;
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
        target.create(truck);
        Assertions.assertEquals(target.getById(truck.getId()).get(), truck);
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
        truck.setBrand(Brand.BMW);
        final boolean actual = target.update(truck);
        Assertions.assertTrue(actual);
    }


    @Test
    void delete() {
        Truck deleted = target.delete(truck.getId());
        Assertions.assertEquals(truck, deleted);
    }
}