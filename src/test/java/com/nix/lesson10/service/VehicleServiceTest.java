package com.nix.lesson10.service;

import com.nix.lesson10.model.Auto;
import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Truck;
import com.nix.lesson10.model.Type;
import com.nix.lesson10.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleServiceTest {

    VehicleService<Auto> target;

    private AutoRepository repository;

    private Auto auto;

    @BeforeEach
    void setUp() {
        repository = new AutoRepository();
        target = new AutoService(repository);
        auto = target.createRandom();
    }

    @Test
    void createList() {
        final List<Auto> autos = target.createList(5);
        target.saveList(autos);
        Assertions.assertEquals(5, autos.size());
    }

    @Test
    void getRandomManufacturer() {
        Assertions.assertTrue(target.getRandomManufacturer() instanceof Brand);
    }

    @Test
    void printAll() {
        List<Auto> autos = List.of(target.createRandom(), target.createRandom());
        Mockito.when(repository.getAll()).thenReturn(autos);
        target.printAll();
    }

    @Test
    void saveList() {
        final List<Auto> actual = target.createList(5);
        boolean save = target.saveList(actual);
        Assertions.assertTrue(save);
    }

    @Test
    void save() {
        final Auto a = target.createRandom();
        boolean save = target.save(a);
        Assertions.assertTrue(save);
    }

    @Test
    void getRandomBodyType() {
        Assertions.assertTrue(target.getRandomBodyType() instanceof Type);
    }
}