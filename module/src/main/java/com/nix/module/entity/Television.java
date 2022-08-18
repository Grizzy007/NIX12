package com.nix.module.entity;

import java.math.BigDecimal;

public class Television extends Electronics {
    private double diagonal;
    private String country;

    public Television() {
    }

    public Television(String series, Screen screenType, BigDecimal price, double diagonal, String country) {
        super(series, screenType, price);
        this.diagonal = diagonal;
        this.country = country;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Television " +
                "series='" + series + '\'' +
                ", screenType=" + screenType +
                ", price=" + price +
                ", diagonal=" + diagonal +
                ", country='" + country;
    }
}
