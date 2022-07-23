package com.nix.lesson10.repository;

import com.nix.lesson10.model.comparator.PriceComparator;
import com.nix.lesson10.model.vehicle.Truck;

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

    @Override
    public void compare(){
        trucks.sort(new PriceComparator()
                .thenComparing((o1, o2) -> o1.getModel().compareTo(o2.getModel()))
                .thenComparing((o1, o2) -> o1.getModel().length() - o2.getModel().length()));
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
