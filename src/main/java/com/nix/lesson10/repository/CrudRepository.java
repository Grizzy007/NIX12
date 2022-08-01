package com.nix.lesson10.repository;

import com.nix.lesson10.model.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Vehicle> {
    List<T> getAll();

    Optional<T> getById(String id);

    T create(T vehicle);

    boolean createList(List<T> list);

    boolean update(T vehicle);

    T delete(String id);
}
