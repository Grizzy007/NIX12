package com.nix.lesson10.model;

import java.math.BigDecimal;

public class Auto extends Vehicle {
    private Type bodyType;

    public Auto(String model, BigDecimal price, Brand manufacturer, Type bodyType) {
        super(model, price, manufacturer);
        this.bodyType = bodyType;
    }

    public Type getBodyType() {
        return bodyType;
    }

    public void setBodyType(Type bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public String toString() {
        return manufacturer + " " + model +
                ", bodyType = " + bodyType +
                ", price = " + price + "$";
    }
}
