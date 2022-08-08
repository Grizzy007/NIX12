package com.nix.lesson10.model.functionals;

import com.nix.lesson10.model.AutoBuilder;
import com.nix.lesson10.model.vehicle.*;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Function<Map<String, Object>, Auto> toAuto = m ->
        {
            AutoBuilder builder = new AutoBuilder();
            builder.buildModel((String) m.get("model"));
            builder.buildPrice(BigDecimal.valueOf(Double.parseDouble(m.get("price").toString())));
            builder.buildManufacturer(Brand.valueOf(m.get("brand").toString()));
            builder.buildCreated(LocalDateTime.parse(m.get("created").toString(), formatter));
            builder.buildBodyType(Type.valueOf(m.get("type").toString()));
            builder.buildEngine(new Engine(Double.parseDouble(m.get("volume").toString()),
                    Brand.valueOf(m.get("brand").toString()),
                    Integer.parseInt(m.get("valves").toString())));
            return builder.getAuto();
        };
        return toAuto.apply(map);
    }
}
