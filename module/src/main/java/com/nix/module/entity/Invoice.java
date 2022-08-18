package com.nix.module.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Invoice {
    private List<Electronics> electronics;
    private LocalDateTime created;
    private Customer customer;
    private Type type;

    public Invoice(List<Electronics> electronics, LocalDateTime created, Customer customer, Type type) {
        this.electronics = electronics;
        this.created = created;
        this.customer = customer;
        this.type = type;
    }

    public Invoice() {
    }

    public List<Electronics> getElectronics() {
        return electronics;
    }

    public void setElectronics(List<Electronics> electronics) {
        this.electronics = electronics;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Invoice " +
                "electronics =" + electronics +
                " Customer age = " + customer.getAge() +
                ", created = " + created +
                ", type=" + type;
    }
}
