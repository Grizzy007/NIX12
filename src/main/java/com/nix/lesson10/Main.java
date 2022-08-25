package com.nix.lesson10;

import com.nix.lesson10.ui.UserInterface;


public class Main {
    public static void main(String[] args) {

        UserInterface ui = new UserInterface();
        ui.start();
//        DBInvoiceRepository db = DBInvoiceRepository.getInstance();
//        Invoice i = new Invoice();
//        i.setId("6");
//        i.setCreated(LocalDateTime.now());
//        List<Vehicle> aL = new ArrayList<>();
//        Auto.AutoBuilder a = new Auto.AutoBuilder();
//        a.buildManufacturer(Brand.AUDI);
//        a.buildCreated(LocalDateTime.now());
//        a.buildEngine(new Engine(4, Brand.AUDI, 8));
//        a.buildPrice(BigDecimal.valueOf(15000));
//        a.buildModel("A7");
//        a.buildBodyType(Type.SUV);
//        aL.add(a.getAuto());
//        Auto a1 = new Auto("Golf 5", BigDecimal.valueOf(9500), Brand.VOLKSWAGEN, Type.SEDAN, 2, 4);
//        Auto a2 = new Auto("Accord", BigDecimal.valueOf(9500), Brand.HONDA, Type.SEDAN, 2, 4);
//        Auto a3 = new Auto("M3", BigDecimal.valueOf(9500), Brand.BMW, Type.SEDAN, 2, 4);
//        aL.add(a1);
//        aL.add(a2);
//        aL.add(a3);
//        aL.add(new Motorcycle("Moto", BigDecimal.valueOf(1500), Brand.HONDA, 120, 2, 4));
//        aL.add(new Truck("LT ", BigDecimal.valueOf(6500), Brand.VOLKSWAGEN, 10, 2, 4));
//        i.setVehicles(aL);
//        db.create(i);
//        List<Invoice> invoices = db.getAll();
//        invoices.forEach(System.out::println);
    }
}

