package com.example.model;

import com.example.utils.ProductSaver;
import lombok.Setter;

import java.util.Objects;

@Setter
public class NotifiableProduct extends Product implements Generatable {

    @Override
    public String generateAddressForNotification() {
        return "somerandommail@gmail.com";
    }

    @Override
    public String toString() {
        return "NotifiableProduct{" +
                "channel='" + channel + '\'' +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public void save(ProductSaver visitor) {
        visitor.saveNotifiableProduct(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NotifiableProduct that = (NotifiableProduct) o;
        return Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), channel);
    }
}