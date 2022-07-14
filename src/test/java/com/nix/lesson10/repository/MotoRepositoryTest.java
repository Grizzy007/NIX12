package com.nix.lesson10.repository;

import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Motorcycle;
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
        target = new MotoRepository();
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
        final Motorcycle createMoto = new Motorcycle("Model", BigDecimal.ONE, Brand.HONDA, 120);
        Motorcycle motorcycle = target.create(createMoto);
        Assertions.assertEquals(createMoto, motorcycle);
    }

    @Test
    void updateNotFound() {
        final Motorcycle otherMoto = createMoto();
        final boolean actual = target.update(otherMoto);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        final Motorcycle m = target.getById(moto.getId()).get();
        m.setBrand(Brand.BMW);
        final boolean actual = target.update(m);
        Assertions.assertTrue(actual);
    }


    @Test
    void delete() {
        final Motorcycle toDelete = new Motorcycle("Model", BigDecimal.ONE, Brand.AUDI, 80);
        target.create(toDelete);
        Motorcycle deleted = target.delete(toDelete.getId());
        Assertions.assertEquals(toDelete, deleted);
    }
}