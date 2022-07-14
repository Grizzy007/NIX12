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
    void getById() {
        Optional<Auto> autoToCompare = target.getById(auto.getId());
    }

    @Test
    void create() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}