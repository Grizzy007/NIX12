package com.nix.lesson10.repository;

import com.nix.lesson10.model.vehicle.Truck;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TruckRepository implements CrudRepository<Truck> {
    private final List<Truck> trucks;

    private static TruckRepository instance;

    private TruckRepository() {
        trucks = new LinkedList<>();
    }

    public static TruckRepository getInstance() {
        if (instance == null) {
            instance = new TruckRepository();
        }
        return instance;
    }

    @Override
    public List<Truck> getAll() {
        return trucks;
    }

    @Override
    public Optional<Truck> getById(String id) {
        return trucks.stream()
                .filter(auto -> auto.getId().equals(id))
                .findAny();
    }

    @Override
    public Truck create(Truck truck) {
        trucks.add(truck);
        return truck;
    }

    @Override
    public boolean createList(List<Truck> list) {
        trucks.addAll(list);
        return true;
    }

    @Override
    public boolean update(Truck truck) {
        Optional<Truck> founded = getById(truck.getId());
        TruckRepository.AutoCopy.copy(truck, founded.orElseGet(() -> {
            System.out.println("No such truck! Updated random truck!");
            return trucks.stream().findAny().get();
        }));
        return true;
    }

    @Override
    public Truck delete(String id) {
        Truck toDelete = getById(id).orElseThrow();
        trucks.remove(toDelete);
        return toDelete;
    }

    private static class AutoCopy {
        static void copy(final Truck from, final Truck to) {
            to.setBrand(from.getBrand());
            to.setModel(from.getModel());
            to.setCapacity(from.getCapacity());
            to.setPrice(from.getPrice());
        }
    }
}
