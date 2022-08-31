package com.nix.lesson10.model.vehicle;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private double volume;
    @Enumerated(value = EnumType.STRING)
    private Brand brand;
    private int valves;

    public Engine(double volume, Brand brand, int valves) {
        this.volume = volume;
        this.brand = brand;
        this.valves = valves;
    }

    public Engine() {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return brand + " " + id +
                " volume=" + volume +
                ", valves=" + valves;
    }
}
