package com.nix.lesson10.repository.collection;

import com.nix.lesson10.annotations.Singleton;
import com.nix.lesson10.model.vehicle.Motorcycle;
import com.nix.lesson10.repository.CrudRepository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Singleton
public class MotoRepository implements CrudRepository<Motorcycle> {
    private final List<Motorcycle> motos;

    private static MotoRepository instance;

    private MotoRepository() {
        motos = new LinkedList<>();
    }

    public static MotoRepository getInstance() {
        if (instance == null) {
            instance = new MotoRepository();
        }
        return instance;
    }

    @Override
    public List<Motorcycle> getAll() {
        return motos;
    }

    @Override
    public Optional<Motorcycle> getById(String id) {
        return motos.stream()
                .filter(auto -> auto.getId().equals(id))
                .findAny();
    }

    @Override
    public Motorcycle create(Motorcycle moto) {
        motos.add(moto);
        return moto;
    }

    @Override
    public boolean createList(List<Motorcycle> list) {
        motos.addAll(list);
        return true;
    }

    @Override
    public boolean update(Motorcycle moto) {
        Optional<Motorcycle> founded = getById(moto.getId());
        founded.ifPresentOrElse(
                moto1 -> MotoRepository.AutoCopy.copy(moto, founded.get()),
                () -> System.out.println("There isn't such motorcycle"));
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
