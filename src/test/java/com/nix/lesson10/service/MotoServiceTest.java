package com.nix.lesson10.service;

import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Motorcycle;
import com.nix.lesson10.model.Truck;
import com.nix.lesson10.repository.MotoRepository;
import com.nix.lesson10.repository.TruckRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MotoServiceTest {
    private MotoService target;
    private MotoRepository motoRepository;

    @BeforeEach
    void setUp() {
        motoRepository = Mockito.mock(MotoRepository.class);
        target = new MotoService(motoRepository);
    }

    private Motorcycle createMoto() {
        return new Motorcycle("Model", BigDecimal.ZERO, Brand.VOLKSWAGEN, 110);
    }

    @Test
    void createAutosNegative() {
        final List<Motorcycle> actual = target.createMotos(-1);
        target.saveMotos(actual);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createZeroAutos() {
        final List<Motorcycle> actual = target.createMotos(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void saveAutos() {
        final List<Motorcycle> actual = target.createMotos(5);
        boolean save = target.saveMotos(actual);
        Assertions.assertTrue(save);
    }

    @Test
    void getByIdNull(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        motoRepository.getById(null);
        Mockito.verify(motoRepository).getById(captor.capture());
        Assertions.assertEquals(null, captor.getValue());
    }

    @Test
    void  printAll(){
        List<Motorcycle> motos = List.of(createMoto(), createMoto());
        Mockito.when(motoRepository.getAll()).thenReturn(motos);
        target.printAll();
    }

}