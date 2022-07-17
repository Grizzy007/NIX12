package com.nix.lesson10.service;

import com.nix.lesson10.matcher.CustomMatcher;
import com.nix.lesson10.model.Auto;
import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Type;
import com.nix.lesson10.repository.AutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.List;

class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = new AutoService(autoRepository);
    }

    private Auto createAuto() {
        return new Auto("Model", BigDecimal.ZERO, Brand.TOYOTA, Type.SUV);
    }

    @Test
    void createAutosNegative() {
        final List<Auto> actual = target.createList(-1);
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                target.saveList(actual);
            }
        });
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
    void createAutos(){
        Auto auto = createAuto();
        autoRepository.create(auto);
        autoRepository.create(auto);
        autoRepository.create(auto);
        Mockito.verify(autoRepository, Mockito.atLeast(2)).create(auto);
    }

    @Test
    void getByIdNull(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        autoRepository.getById(null);
        Mockito.verify(autoRepository).getById(captor.capture());
        Assertions.assertEquals(null, captor.getValue());
    }

    @Test
    void match(){
        Auto auto = createAuto();
        Mockito.when(autoRepository.create(Mockito.argThat(new CustomMatcher(createAuto())))).thenReturn(auto);
    }

    @Test
    void  printAll(){
        List<Auto> autos = List.of(createAuto(), createAuto());
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        target.printAll();
    }
}