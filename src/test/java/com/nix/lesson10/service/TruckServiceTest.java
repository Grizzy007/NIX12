package com.nix.lesson10.service;

import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Truck;
import com.nix.lesson10.repository.TruckRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

class TruckServiceTest {

    private TruckService target;
    private TruckRepository truckRepository;

    @BeforeEach
    void setUp() {
        truckRepository = Mockito.mock(TruckRepository.class);
        target = TruckService.getInstance();
    }

    private Truck createTruck() {
        return new Truck("Model", BigDecimal.ZERO, Brand.VOLKSWAGEN, 15, 4.2, 6);
    }

    @Test
    void createTrucksNegative() {
        final List<Truck> actual = target.createList(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void saveNegativeTrucks() {
        final List<Truck> actual = target.createList(-1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveList(actual));
    }

    @Test
    void createZeroAutos() {
        final List<Truck> actual = target.createList(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void saveAutos() {
        final List<Truck> actual = target.createList(5);
        boolean save = target.saveList(actual);
        Assertions.assertTrue(save);
    }

    @Test
    void getByIdNull() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        truckRepository.getById(null);
        Mockito.verify(truckRepository).getById(captor.capture());
        Assertions.assertEquals(null, captor.getValue());
    }

    @Test
    void printAll() {
        List<Truck> trucks = List.of(createTruck(), createTruck());
        Mockito.when(truckRepository.getAll()).thenReturn(trucks);
        target.printAll();
    }

}