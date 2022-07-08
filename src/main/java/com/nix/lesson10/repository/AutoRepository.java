package com.nix.lesson10.repository;

import com.nix.lesson10.model.Auto;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    public Auto getById(String id) {
        for (Auto a : autos) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public Auto create(Object auto) {
        Auto newAuto = (Auto) auto;
        autos.add(newAuto);
        return newAuto;
    }

    @Override
    public boolean create(List list) {
        autos.addAll(list);
        return true;
    }

    @Override
    public boolean update(Object auto) {
        Auto autoToUpdate = (Auto) auto;
        final Auto founded = getById(autoToUpdate.getId());
        if (founded != null) {
            AutoCopy.copy(autoToUpdate, founded);
            return true;
        }
        return false;
    }

    @Override
    public Auto delete(String id) {
        final Iterator<Auto> iterator = autos.iterator();
        while (iterator.hasNext()) {
            final Auto auto = iterator.next();
            if (auto.getId().equals(id)) {
                iterator.remove();
                return auto;
            }
        }
        return null;
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
