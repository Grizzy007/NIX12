package com.nix.lesson10.model;

import com.nix.lesson10.model.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Invoice {
    private String id;
    private LocalDateTime created;
    private List<Vehicle> vehicles;

    public Invoice(LocalDateTime created, List<Vehicle> vehicles) {
        this.id = UUID.randomUUID().toString();
        this.created = created;
        this.vehicles = vehicles;
    }

    public Invoice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", created=" + created +
                ", vehicles=" + vehicles +
                '}';
    }
}
