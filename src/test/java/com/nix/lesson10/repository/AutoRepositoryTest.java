package com.nix.lesson10.repository;

import com.nix.lesson10.model.Auto;
import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Type;
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
        target = new AutoRepository();
        auto = createAuto();
        target.create(auto);
    }

    private Auto createAuto() {
        return new Auto("Model", BigDecimal.ZERO, Brand.TOYOTA, Type.SUV);
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
        final Auto createAuto = new Auto("Model", BigDecimal.ONE, Brand.TOYOTA, Type.SUV);
        Auto auto1 = target.create(createAuto);
        Assertions.assertEquals(createAuto, auto1);
    }

    @Test
    void updateNotFound() {
        final Auto otherAuto = createAuto();
        final boolean actual = target.update(otherAuto);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        final Auto a = target.getById(auto.getId()).get();
        a.setBrand(Brand.BMW);
        final boolean actual = target.update(a);;
        Assertions.assertTrue(actual);
    }


    @Test
    void delete() {
        final Auto toDelete = new Auto("Model", BigDecimal.ONE, Brand.BMW, Type.JEEP);
        target.create(toDelete);
        Auto deleted = target.delete(toDelete.getId());
        Assertions.assertEquals(toDelete,deleted);
    }
}