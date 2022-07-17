package com.nix.lesson10.matcher;

import com.nix.lesson10.model.Auto;
import org.mockito.ArgumentMatcher;

public class CustomMatcher implements ArgumentMatcher<Auto> {

    private Auto auto;

    public CustomMatcher(Auto auto) {
        this.auto = auto;
    }

    @Override
    public boolean matches(Auto car) {
        return auto.getPrice().equals(car.getPrice()) &&
                auto.getBodyType().equals(car.getBodyType()) &&
                auto.getBrand().equals(car.getBrand()) &&
                auto.getId() != null;
    }
}
