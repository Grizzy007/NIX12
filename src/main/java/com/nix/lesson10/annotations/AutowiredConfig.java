package com.nix.lesson10.annotations;

import com.nix.lesson10.service.VehicleService;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

public class AutowiredConfig {

    public void configure() {
        Reflections reflections = new Reflections("com.nix.lesson10.service");
        final Set<Class<? extends VehicleService>> classes = reflections.getSubTypesOf(VehicleService.class);
        classes.forEach(c -> {
            final Constructor[] constructors = c.getDeclaredConstructors();
            for (Constructor con : constructors) {
                if (con.isAnnotationPresent(Autowired.class)) {
                    try {
                        Field f = c.getField("repository");
                        f.setAccessible(true);
                        f.set(f.getType(),c.newInstance());
                        System.out.println("Done");
                    } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}