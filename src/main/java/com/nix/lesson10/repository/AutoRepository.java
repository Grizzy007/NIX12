package com.nix.lesson10.repository;

import com.nix.lesson10.annotations.Singleton;
import com.nix.lesson10.model.vehicle.Auto;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Singleton
public class AutoRepository implements CrudRepository<Auto> {
    private final List<Auto> autos;

    private static AutoRepository instance;

    private AutoRepository() {
        autos = new LinkedList<>();
    }

    public static AutoRepository getInstance() {
        if (instance == null) {
            instance = new AutoRepository();
        }
        return instance;
    }

    @Override
    public List<Auto> getAll() {
        return autos;
    }

    @Override
    public Optional<Auto> getById(String id) {
        return autos.stream()
                .filter(auto -> auto.getId().equals(id))
                .findAny();
    }

    @Override
    public Auto create(Auto auto) {
        autos.add(auto);
        return auto;
    }

    @Override
    public boolean createList(List<Auto> list) {
        autos.addAll(list);
        return true;
    }

    @Override
    public boolean update(Auto auto) {
        Optional<Auto> founded = getById(auto.getId());
        founded.ifPresent(auto1 -> AutoCopy.copy(auto1, founded.get()));
        return founded.isPresent();
    }

    @Override
    public Auto delete(String id) {
        Auto toDelete = getById(id).orElseThrow();
        autos.remove(toDelete);
        return toDelete;
    }

    private static class AutoCopy {
        static void copy(final Auto from, final Auto to) {
            to.setBrand(from.getBrand());
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
        }
    }

}
