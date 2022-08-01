package com.nix.lesson10.service;


import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Type;
import com.nix.lesson10.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleServiceTest {

    VehicleService<Auto> target;

    private AutoRepository repository;

    private Auto auto;



    @BeforeEach
    void setUp() {
        repository = Mockito.mock(AutoRepository.class);
        target = AutoService.getInstance();
        auto = target.createRandom();
    }

    @Test
    void argMatcher() {
        List<Auto> mocklist = Mockito.mock(List.class);

        Mockito.when(mocklist.get(Mockito.anyInt())).thenReturn(auto);

        assertEquals(auto, mocklist.get(0));
        assertEquals(auto, mocklist.get(1));
        assertEquals(auto, mocklist.get(2));
    }

    @Test
    void createWithException(){
        List<Auto> autos = new LinkedList<>();
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                target.saveList(autos);
            }
        });
    }


    @Test
    void realMethod(){
        auto = Mockito.mock(Auto.class);
        Mockito.when(auto.getBodyType()).thenReturn(Type.SEDAN);
        Mockito.when(auto.getBrand()).thenReturn(Brand.VOLKSWAGEN);
        Mockito.when(auto.toString()).thenCallRealMethod();
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
    void saveList() {
        final List<Auto> actual = target.createList(5);
        boolean save = target.saveList(actual);
        Assertions.assertTrue(save);
    }

    @Test
    void saveAutos(){
        repository.create(auto);
        repository.create(auto);
        repository.create(auto);
        Mockito.verify(repository, Mockito.times(3)).create(auto);
    }

    @Test
    void save() {
        boolean save = target.save(auto);
        Assertions.assertTrue(save);
    }

    @Test
    void getRandomBodyType() {
        Assertions.assertTrue(target.getRandomBodyType() instanceof Type);
    }
}