package com.example.model;

import lombok.Setter;

import java.util.Objects;

@Setter
public class ProductBundle extends Product implements Countable{
    protected int amount;

    @Override
    public int getAmountInBundle() {
        return amount;
    }

    @Override
    public String toString() {
        return "ProductBundle{" +
                "channel='" + channel + '\'' +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amountInBundle=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductBundle that = (ProductBundle) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount);
    }
}