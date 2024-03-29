package com.nix.lesson10.service;

import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Type;
import com.nix.lesson10.model.vehicle.Vehicle;
import com.nix.lesson10.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public abstract class VehicleService<T extends Vehicle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected static final Random RANDOM = new Random();
    protected final CrudRepository<T> repository;

    public abstract T create(BufferedReader bf) throws IOException;

    protected abstract T createRandom();

    public abstract void compare();

    public abstract void update(BufferedReader reader) throws IOException;

    public void delete(BufferedReader reader) throws IOException {
        String id = getId(reader);
        repository.delete(id);
        LOGGER.debug("Vehicle deleted {}", id);
    }

    public List<T> createList(int count) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T truck = createRandom();
            result.add(truck);
            LOGGER.debug("Created vehicle {}", truck.getId());
        }
        return result;
    }

    public BigDecimal sumOfAllVehicle() {
        return repository.getAll().stream()
                .map(Vehicle::getPrice)
                .reduce((price1, price2) -> price1.add(price2))
                .orElse(BigDecimal.ZERO);
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
        if (autos.isEmpty()) {
            throw new IllegalArgumentException();
        }
        repository.createList(autos);
        return true;
    }

    public void priceMoreThan(int compare) {
        repository.getAll().stream()
                .filter(vehicle -> vehicle.getPrice().intValue() > compare)
                .forEach(System.out::println);
    }

    protected Type getRandomBodyType() {
        final Type[] values = Type.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    protected String getId(BufferedReader reader) throws IOException {
        List<T> vehicles = repository.getAll();
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println(i + ". " + vehicles.get(i).toString());
        }
        System.out.println("Input number of auto that you want to see a body type: ");
        int index = Integer.parseInt(reader.readLine());
        return vehicles.get(index).getId();
    }
}
