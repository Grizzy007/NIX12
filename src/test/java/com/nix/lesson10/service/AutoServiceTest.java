package com.nix.lesson10.service;

import com.nix.lesson10.model.AutoBuilder;
import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Engine;
import com.nix.lesson10.model.vehicle.Type;
import com.nix.lesson10.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = AutoService.getInstance();
    }

    private Auto createAuto() {
        AutoBuilder builder = new AutoBuilder();
        builder.buildModel("Model");
        builder.buildPrice(BigDecimal.valueOf(1200));
        builder.buildManufacturer(Brand.AUDI);
        builder.buildCreated(LocalDateTime.now());
        builder.buildBodyType(Type.SUV);
        builder.buildEngine(new Engine(4,
                Brand.AUDI,
                4));
        return builder.getAuto();
    }

    @Test
    void createAutosNegative() {
        final List<Auto> actual = target.createList(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void saveNegativeAutos() {
        final List<Auto> actual = target.createList(-1);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveList(actual));
    }


    @Test
    void createZeroAutos() {
        final List<Auto> actual = target.createList(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void saveAutos() {
        final List<Auto> actual = target.createList(5);
        boolean save = target.saveList(actual);
        Assertions.assertTrue(save);
    }

    @Test
    void createAutos() {
        Auto auto = createAuto();
        autoRepository.create(auto);
        autoRepository.create(auto);
        autoRepository.create(auto);
        Mockito.verify(autoRepository, Mockito.atLeast(2)).create(auto);
    }

    @Test
    void getByIdNull() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        autoRepository.getById(null);
        Mockito.verify(autoRepository).getById(captor.capture());
        Assertions.assertEquals(null, captor.getValue());
    }

    @Test
    void match() {
        Auto auto = createAuto();
        Mockito.when(autoRepository.create(Mockito.argThat(new ArgumentMatcher<>() {
            Auto auto;

            @Override
            public boolean matches(Auto car) {
                return auto.getPrice().equals(car.getPrice()) &&
                        auto.getBodyType().equals(car.getBodyType()) &&
                        auto.getBrand().equals(car.getBrand()) &&
                        auto.getId() != null;
            }
        }))).thenReturn(auto);
    }

    @Test
    void printAll() {
        List<Auto> autos = List.of(createAuto(), createAuto());
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        target.printAll();
    }
}