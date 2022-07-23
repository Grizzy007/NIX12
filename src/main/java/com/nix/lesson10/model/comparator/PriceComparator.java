package com.nix.lesson10.model.comparator;

import com.nix.lesson10.model.vehicle.Vehicle;

import java.util.Comparator;

public class PriceComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getPrice().intValue() - o2.getPrice().intValue();
    }
}
