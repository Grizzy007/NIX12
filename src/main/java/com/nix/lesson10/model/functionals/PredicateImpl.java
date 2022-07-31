package com.nix.lesson10.model.functionals;

import com.nix.lesson10.model.vehicle.Vehicle;

import java.util.List;

public class PredicateImpl {
    public boolean check(List<Vehicle> vehicles) {//Predicate realisation 3
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPrice().equals(null)) {
                return false;
            }
        }
        return true;
    }
}
