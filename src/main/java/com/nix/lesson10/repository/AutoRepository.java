package com.nix.lesson10.repository;

import com.nix.lesson10.model.comparator.PriceComparator;
import com.nix.lesson10.model.vehicle.Auto;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AutoRepository implements CrudRepository<Auto> {
    private final List<Auto> autos;

    public AutoRepository() {
        autos = new LinkedList<>();
    }

    @Override
    public List<Auto> getAll() {
        return autos;
    }

    @Override
    public Optional<Auto> getById(String id) {
        for (Auto a : autos) {
            if (a.getId().equals(id)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
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

    @Override
    public void compare(){
        autos.sort(new PriceComparator()
                .thenComparing((o1, o2) -> o1.getModel().compareTo(o2.getModel()))
                .thenComparing((o1, o2) -> o1.getModel().length() - o2.getModel().length()));
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
