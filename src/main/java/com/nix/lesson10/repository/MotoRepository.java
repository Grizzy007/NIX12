package com.nix.lesson10.repository;

import com.nix.lesson10.model.Motorcycle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MotoRepository implements CrudRepository<Motorcycle> {
    private final List<Motorcycle> motos;

    public MotoRepository() {
        motos = new LinkedList<>();
    }

    @Override
    public List<Motorcycle> getAll() {
        return motos;
    }

    @Override
    public Optional<Motorcycle> getById(String id) {
        for (Motorcycle a : motos) {
            if (a.getId().equals(id)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

    @Override
    public Motorcycle create(Motorcycle auto) {
        motos.add(auto);
        return auto;
    }

    @Override
    public boolean create(List<Motorcycle> list) {
        motos.addAll(list);
        return true;
    }

    @Override
    public boolean update(Motorcycle moto) {
        Optional<Motorcycle> founded = getById(moto.getId());
        founded.ifPresentOrElse(
                moto1 -> MotoRepository.AutoCopy.copy(moto, founded.get()),
                ()-> System.out.println("There isn't such motorcycle"));
        return founded.isPresent();
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
