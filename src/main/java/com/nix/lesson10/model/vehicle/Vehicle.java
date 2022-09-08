package com.nix.lesson10.model.vehicle;

import com.nix.lesson10.model.Invoice;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;
    protected String model;
    protected BigDecimal price;
    @Transient
    protected char currency;
    @Enumerated(value = EnumType.STRING)
    protected Brand manufacturer;
    protected LocalDateTime created;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "Engine_id")
    protected Engine engine;
    @ManyToMany(mappedBy = "vehicles")
    private Set<Invoice> invoices = new LinkedHashSet<>();

    protected Vehicle(String model, BigDecimal price, Brand manufacturer, double volume, int valves) {
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
        this.engine = new Engine(volume, manufacturer, valves);
        currency = '$';
        created = LocalDateTime.now().minusHours((long) (Math.random() * 25));
    }

    protected Vehicle(String model, BigDecimal price, char currency, Brand manufacturer, LocalDateTime created,
                      double volume, int valves) {
        this.model = model;
        this.price = price;
        this.currency = currency;
        this.manufacturer = manufacturer;
        this.created = created;
        this.engine = new Engine(volume, manufacturer, valves);
    }

    protected Vehicle() {
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Brand getBrand() {
        return manufacturer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBrand(Brand manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", manufacturer=" + manufacturer +
                ", created=" + created +
                ", engine=" + engine +
                ", invoices=" + invoices +
                '}';
    }

}
