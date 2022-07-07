package com.nix.lesson10.repository;

import com.nix.lesson10.model.Truck;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TruckRepository implements CrudRepository {
    private final List<Truck> trucks;

    public TruckRepository() {
        trucks = new LinkedList<>();
    }

    @Override
    public List<Truck> getAll() {
        return trucks;
    }

    @Override
    public Truck getById(String id) {
        for (Truck a : trucks) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public Truck create(Object auto) {
        Truck newTruck = (Truck) auto;
        trucks.add(newTruck);
        return newTruck;
    }

    @Override
    public boolean create(List list) {
        trucks.addAll(list);
        return true;
    }

    @Override
    public boolean update(Object truck) {
        Truck truckToUpdate = (Truck) truck;
        final Truck founded = getById(truckToUpdate.getId());
        if (founded != null) {
            TruckRepository.AutoCopy.copy(truckToUpdate, founded);
            return true;
        }
        return false;
    }

    @Override
    public Truck delete(String id) {
        final Iterator<Truck> iterator = trucks.iterator();
        while (iterator.hasNext()) {
            final Truck truck = iterator.next();
            if (truck.getId().equals(id)) {
                iterator.remove();
                return truck;
            }
        }
        return null;
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
