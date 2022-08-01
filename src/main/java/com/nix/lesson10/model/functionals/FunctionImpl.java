package com.nix.lesson10.model.functionals;

import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Type;
import com.nix.lesson10.model.vehicle.Vehicle;

import java.math.BigDecimal;
import java.util.Map;

public class FunctionImpl {
    public Vehicle toAuto(Map<String, Object> map) {
        Object[] propreties = new Object[map.size()];
        int i = 0;
        for(Object obj: map.values()){
            propreties[i] = obj;
        }
        Auto auto = new Auto();
        auto.setModel((String) propreties[0]);
        auto.setPrice((BigDecimal) propreties[1]);
        auto.setBrand((Brand) propreties[2]);
        auto.setBodyType((Type) propreties[3]);
        return auto;
    }
}
