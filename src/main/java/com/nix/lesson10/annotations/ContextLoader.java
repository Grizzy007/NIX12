package com.nix.lesson10.annotations;

import com.nix.lesson10.repository.collection.AutoRepository;
import com.nix.lesson10.service.AutoService;
import com.nix.lesson10.service.MotoService;
import com.nix.lesson10.service.TruckService;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContextLoader {
    Map<Class<?>, Object> cache = new HashMap<>();

    @SneakyThrows
    public void singletonClasses() {
        Reflections reflection = new Reflections("com");
        Set<Class<?>> set = reflection.getTypesAnnotatedWith(Singleton.class);
        for (Class<?> classInstance : set) {
            Field[] fields = classInstance.getDeclaredFields();
            Constructor<?>[] constructors = classInstance.getDeclaredConstructors();
            for (Constructor<?> declaredConstructor : constructors) {
                instanceWithoutParameters(fields, declaredConstructor, classInstance);
                instanceWithParameters(declaredConstructor, fields, classInstance);
            }
        }
    }

    @SneakyThrows
    private void assignInstance(Field[] fields, Class<?> classInstance, Object o) {
        for (Field field : fields) {
            if (field.getName().equals("instance")) {
                field.setAccessible(true);
                field.set(null, o);
                cache.put(classInstance, o);
            }
        }
    }

    @SneakyThrows
    private void instanceWithoutParameters(Field[] fields, Constructor<?> declaredConstructor, Class<?> classInstance) {
        if (declaredConstructor.getParameterCount() == 0) {
            for (Field field : fields) {
                if (field.getName().equals("instance")) {
                    field.setAccessible(true);
                    field.set(null, declaredConstructor.newInstance());
                    cache.put(classInstance, declaredConstructor.newInstance());
                }
            }
            cache.put(classInstance, declaredConstructor.newInstance());
        }
    }

    private void instanceWithParameters(Constructor<?> declaredConstructor, Field[] fields, Class<?> classInstance) {
        try {
            if (declaredConstructor.isAnnotationPresent(Autowired.class)
                    && declaredConstructor.getParameterCount() == 1) {
                if (AutoService.class.equals(classInstance)) {
                    Object obj = declaredConstructor.newInstance(cache.get(AutoRepository.class));
                    assignInstance(fields, classInstance, obj);
                }
                if (TruckService.class.equals(classInstance)) {
                    Object obj = declaredConstructor.newInstance(cache.get(TruckService.class));
                    assignInstance(fields, classInstance, obj);
                }
                if (MotoService.class.equals(classInstance)) {
                    Object obj = declaredConstructor.newInstance(cache.get(MotoService.class));
                    assignInstance(fields, classInstance, obj);
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}