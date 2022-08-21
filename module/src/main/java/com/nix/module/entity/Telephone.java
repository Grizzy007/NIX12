package com.nix.module.entity;

import java.math.BigDecimal;

public class Telephone extends Electronics {
    private String model;

    public Telephone() {
    }

    public Telephone(String series, Screen screenType, BigDecimal price, String model) {
        super(series, screenType, price);
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Telephone " +
                "series='" + series + '\'' +
                ", screenType=" + screenType +
                ", price=" + price +
                ", model='" + model;
    }
}
