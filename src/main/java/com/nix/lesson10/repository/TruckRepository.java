package com.nix.lesson10.repository;

import com.nix.lesson10.model.Truck;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TruckRepository implements CrudRepository<Truck> {
    private final List<Truck> trucks;

    public TruckRepository() {
        trucks = new LinkedList<>();
    }

    @Override
    public List<Truck> getAll() {
        return trucks;
    }

    @Override
    public Optional<Truck> getById(String id) {
        for (Truck a : trucks) {
            if (a.getId().equals(id)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
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
