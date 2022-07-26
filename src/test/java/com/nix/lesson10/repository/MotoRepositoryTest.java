package com.nix.lesson10.repository;

import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Motorcycle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class MotoRepositoryTest {

    private MotoRepository target;

    private Motorcycle moto;

    @BeforeEach
    void setUp() {
        target = MotoRepository.getInstance();
        moto = createMoto();
        target.create(moto);
    }

    private Motorcycle createMoto() {
        return new Motorcycle("Model", BigDecimal.ZERO, Brand.TOYOTA, 100);
    }

    @Test
    void getAll() {
        final List<Motorcycle> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void getById_find() {
        final Optional<Motorcycle> optional = target.getById(moto.getId());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(moto.getId(), optional.get().getId());
    }

    @Test
    void getByIdNoFind() {
        final Optional<Motorcycle> optional = target.getById("123");
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void create() {
        target.create(moto);
        Assertions.assertEquals(target.getById(moto.getId()).get(), moto);
    }

    @Test
    void updateNotFound() {
        final Motorcycle otherMoto = createMoto();
        final boolean actual = target.update(otherMoto);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        moto.setBrand(Brand.BMW);
        final boolean actual = target.update(moto);
        Assertions.assertTrue(actual);
    }


    @Test
    void delete() {
        Motorcycle deleted = target.delete(moto.getId());
        Assertions.assertEquals(moto, deleted);
    }
}