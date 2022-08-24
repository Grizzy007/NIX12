package com.nix.lesson10.model.vehicle;

public class Engine {
    private String id;
    private double volume;
    private Brand brand;
    private int valves;

    public Engine(double volume, Brand brand, int valves) {
        id = String.valueOf(Math.random() * 10 + 1);
        this.volume = volume;
        this.brand = brand;
        this.valves = valves;
    }


    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getValves() {
        return valves;
    }

    public void setValves(int valves) {
        this.valves = valves;
    }

    @Override
    public String toString() {
        return brand + " " + id +
                " volume=" + volume +
                ", valves=" + valves;
    }
}
