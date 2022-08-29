package com.nix.lesson10.service;

import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Motorcycle;
import com.nix.lesson10.repository.collection.MotoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class MotoServiceTest {
    private MotoService target;
    private MotoRepository motoRepository;

    @BeforeEach
    void setUp() {
        motoRepository = Mockito.mock(MotoRepository.class);
        target = MotoService.getInstance();
    }

    private Motorcycle createMoto() {
        return new Motorcycle("Model", BigDecimal.ZERO, Brand.VOLKSWAGEN, 110, 1.2,2);
    }

    @Test
    void createMotosNegative() {
        final List<Motorcycle> actual = target.createList(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void saveNegativeMotos() {
        final List<Motorcycle> actual = target.createList(-1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveList(actual));
    }

    @Test
    void createZeroAutos() {
        final List<Motorcycle> actual = target.createList(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void saveAutos() {
        final List<Motorcycle> actual = target.createList(5);
        boolean save = target.saveList(actual);
        Assertions.assertTrue(save);
    }

    @Test
    void getByIdNull() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        motoRepository.getById(null);
        Mockito.verify(motoRepository).getById(captor.capture());
        Assertions.assertEquals(null, captor.getValue());
    }

    @Test
    void printAll() {
        List<Motorcycle> motos = List.of(createMoto(), createMoto());
        Mockito.when(motoRepository.getAll()).thenReturn(motos);
        target.printAll();
    }

}