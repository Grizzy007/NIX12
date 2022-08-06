package com.nix.lesson10.model.functionals;

import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Truck;
import com.nix.lesson10.model.vehicle.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Function;

public class FunctionImpl {
    public static Truck toTruck(Map<String, Object> map) {
        Function<Map<String, Object>, Truck> toTruck = m -> new Truck((String) m.get("model"),
                (BigDecimal) m.get("price"), (Brand) m.get("brand"), (Integer) m.get("capacity"),
                (Double) m.get("volume"), (Integer) m.get("valves"));
        return toTruck.apply(map);
    }

    public static Auto toAuto(Map<String, Object> map) {
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Function<Map<String, Object>, Auto> toAuto = m -> new Auto((String) m.get("model"),
                BigDecimal.valueOf(Double.parseDouble(m.get("price").toString())),
                m.get("currency").toString().charAt(0), Brand.valueOf(m.get("brand").toString()),
                LocalDateTime.parse(m.get("created").toString(), formatter), Double.parseDouble(m.get("volume").toString()),
                Integer.parseInt(m.get("valves").toString()), Type.valueOf(m.get("type").toString()));
        return toAuto.apply(map);
    }
}
