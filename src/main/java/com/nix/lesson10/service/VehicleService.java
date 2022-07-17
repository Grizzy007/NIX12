package com.nix.lesson10.service;

import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Type;
import com.nix.lesson10.model.Vehicle;
import com.nix.lesson10.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class VehicleService<T extends Vehicle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected final Random RANDOM = new Random();
    protected CrudRepository<T> repository;

    public abstract T create(BufferedReader bf) throws IOException;

    protected abstract T createRandom();

    public abstract void update(BufferedReader reader) throws IOException;

    public abstract void delete(BufferedReader reader) throws IOException;

    public List<T> createList(int count) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T truck = createRandom();
            result.add(truck);
            LOGGER.debug("Created truck {}", truck.getId());
        }
        return result;
    }

    protected VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    protected Brand getRandomManufacturer() {
        final Brand[] values = Brand.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (T vehicle : repository.getAll()) {
            System.out.println(vehicle);
        }
    }

    public boolean save(T vehicle) {
        repository.create(vehicle);
        return true;
    }

    public boolean saveList(List<T> autos) {
        repository.createList(autos);
        return true;
    }

    protected Type getRandomBodyType() {
        final Type[] values = Type.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
