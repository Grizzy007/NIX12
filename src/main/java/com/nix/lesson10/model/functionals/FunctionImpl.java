package com.nix.lesson10.model.functionals;

import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Truck;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

public class FunctionImpl {
    public static Truck toVehicle(Map<String, Object> map) {
        Function<Map<String, Object>, Truck> toTruck = m -> new Truck((String) m.get("model"),
                (BigDecimal) m.get("price"), (Brand) m.get("brand"), (Integer) m.get("capacity"));
        return toTruck.apply(map);
    }
}
