package com.nix.lesson10.model;

import com.nix.lesson10.model.vehicle.Vehicle;

import java.util.Iterator;
import java.util.Optional;

public interface Garage<T extends Vehicle> {
    T addFirst(T veh, Integer id);

    T addLast(T veh, Integer id);

    int size();

    Iterator<Vehicle> iter();

    T getFirstRestyle();

    T getLastRestyle();

    Optional<? extends Vehicle> autoByRestyle(Integer id);

    Optional<? extends Vehicle> deleteAutoByRestyle(Integer id);

    Optional<? extends Vehicle> replaceAutoByRestyle(Integer id, T veh);
}
