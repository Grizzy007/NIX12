package com.nix.lesson10.repository;

import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class AutoRepositoryTest {

    private AutoRepository target;

    private Auto auto;

    @BeforeEach
    void setUp() {
        target = AutoRepository.getInstance();
        auto = createAuto();
        target.create(auto);
    }

    private Auto createAuto() {
        return new Auto("Model", BigDecimal.ZERO, Brand.TOYOTA, Type.SUV, 2,4);
    }

    @Test
    void getAll() {
        final List<Auto> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void getById_find() {
        final Optional<Auto> optional = target.getById(auto.getId());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(auto.getId(), optional.get().getId());
    }

    @Test
    void getByIdNotFound() {
        final Optional<Auto> optional = target.getById("123");
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void create() {
        target.create(auto);
        Assertions.assertEquals(target.getById(auto.getId()).get(), auto);
    }

    @Test
    void updateNotFound() {
        final Auto otherAuto = createAuto();
        final boolean actual = target.update(otherAuto);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        auto.setBrand(Brand.BMW);
        final boolean actual = target.update(auto);
        Assertions.assertTrue(actual);
    }


    @Test
    void delete() {
        target.create(auto);
        Auto deleted = target.delete(auto.getId());
        Assertions.assertEquals(auto,deleted);
    }
}