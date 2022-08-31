package com.nix.lesson10;

import com.nix.lesson10.config.HibernateConfig;
import com.nix.lesson10.model.vehicle.*;
import com.nix.lesson10.repository.hibernate.HibernateAutoRepository;
import com.nix.lesson10.repository.hibernate.HibernateTruckRepository;
import com.nix.lesson10.ui.UserInterface;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {

        UserInterface ui = new UserInterface();
        ui.start();
//        HibernateConfig.getSessionFactory();
//
//        Engine e = new Engine(2, Brand.BMW, 2);
//        Auto auto = new Auto();
//        auto.setEngine(e);
//        auto.setModel("A5");
//        auto.setBrand(Brand.AUDI);
//        auto.setPrice(BigDecimal.valueOf(15000));
//        auto.setCreated(LocalDateTime.now());
//        auto.setBodyType(Type.CROSSOVER);
//        Truck t = new Truck();
//        t.setEngine(e);
//        t.setCapacity(10);
//        t.setPrice(BigDecimal.valueOf(1500));
//        t.setBrand(Brand.BMW);
//        t.setModel("LT");
//        t.setCreated(LocalDateTime.now());
//        //HibernateTruckRepository th = HibernateTruckRepository.getInstance();
//        //th.create(t);
//        HibernateAutoRepository th = HibernateAutoRepository.getInstance();
//        //th.create(auto);
//        th.getAll().forEach(System.out::println);
    }
}

