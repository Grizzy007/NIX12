package com.nix.lesson10.annotations;

import com.nix.lesson10.repository.CrudRepository;
import com.nix.lesson10.service.VehicleService;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

public class SingletonConfig {
    public void configure() {
        Reflections reflections = new Reflections("com.nix.lesson10.repository");
        final Set<Class<? extends VehicleService>> classes = reflections.getSubTypesOf(VehicleService.class);
        reflections = new Reflections("com.nix.lesson10.service");
        final Set<Class<? extends CrudRepository>> repos = reflections.getSubTypesOf(CrudRepository.class);
        classes.stream()
                .filter(c -> c.isAnnotationPresent(Singleton.class))
                .forEach(cl -> {
                Class<?> bean = cl.getClass();
                Field[] declaredFields = bean.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (declaredField.getName().equals("instance")) {
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(declaredField, cl.newInstance());
                            System.out.println("Finish");
                        } catch (IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
        });
        repos.forEach(cl -> {
            if (cl.isAnnotationPresent(Singleton.class)) {
                Class<?> bean = cl.getClass();
                Field[] declaredFields = bean.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (declaredField.getName().equals("instance")) {
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(declaredField, cl.newInstance());
                            System.out.println("Finish");
                        } catch (IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
