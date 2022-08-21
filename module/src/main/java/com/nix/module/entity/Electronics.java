package com.nix.module.entity;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Electronics {
    protected String id;
    protected String series;
    protected Screen screenType;
    protected BigDecimal price;

    public Electronics() {
        this.id = UUID.randomUUID().toString();
    }

    public Electronics(String series, Screen screenType, BigDecimal price) {
        this.id = UUID.randomUUID().toString();
        this.series = series;
        this.screenType = screenType;
        this.price = price;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Screen getScreenType() {
        return screenType;
    }

    public void setScreenType(Screen screenType) {
        this.screenType = screenType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
