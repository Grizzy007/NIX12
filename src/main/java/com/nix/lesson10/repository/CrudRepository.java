package com.nix.lesson10.repository;

import com.nix.lesson10.model.Vehicle;

import java.util.List;

public interface CrudRepository<T extends Vehicle> {
    List<T> getAll();

    T getById(String id);

    <V> T create(V vehicle);

    boolean create(List<T> list);

    <V> boolean update(V vehicle);

    T delete(String id);
}
