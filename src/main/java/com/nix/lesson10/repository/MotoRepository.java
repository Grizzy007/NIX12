package com.nix.lesson10.repository;

import com.nix.lesson10.model.Motorcycle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MotoRepository implements CrudRepository {
    private final List<Motorcycle> motos;

    public MotoRepository() {
        motos = new LinkedList<>();
    }

    @Override
    public List<Motorcycle> getAll() {
        return motos;
    }

    @Override
    public Motorcycle getById(String id) {
        for (Motorcycle a : motos) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public Motorcycle create(Object auto) {
        Motorcycle newMoto = (Motorcycle) auto;
        motos.add(newMoto);
        return newMoto;
    }

    @Override
    public boolean create(List list) {
        motos.addAll(list);
        return true;
    }

    @Override
    public boolean update(Object auto) {
        Motorcycle motoToUpdate = (Motorcycle) auto;
        final Motorcycle founded = getById(motoToUpdate.getId());
        if (founded != null) {
            MotoRepository.AutoCopy.copy(motoToUpdate, founded);
            return true;
        }
        return false;
    }

    @Override
    public Motorcycle delete(String id) {
        final Iterator<Motorcycle> iterator = motos.iterator();
        while (iterator.hasNext()) {
            final Motorcycle moto = iterator.next();
            if (moto.getId().equals(id)) {
                iterator.remove();
                return moto;
            }
        }
        return null;
    }

    private static class AutoCopy {
        static void copy(final Motorcycle from, final Motorcycle to) {
            to.setBrand(from.getBrand());
            to.setModel(from.getModel());
            to.setLanding(from.getLanding());
            to.setPrice(from.getPrice());
        }
    }
}
