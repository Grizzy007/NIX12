package com.nix.lesson10;

import com.nix.lesson10.model.Invoice;
import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Engine;
import com.nix.lesson10.model.vehicle.Type;
import com.nix.lesson10.repository.collection.VehicleFileReader;
import com.nix.lesson10.repository.db.DBAutoRepository;
import com.nix.lesson10.repository.db.DBInvoiceRepository;
import com.nix.lesson10.repository.db.DBUtil;
import com.nix.lesson10.ui.UserInterface;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws URISyntaxException {

        UserInterface ui = new UserInterface();
        ui.start();
//        DBInvoiceRepository db = DBInvoiceRepository.getInstance();

//        List<Invoice> i = db.getAll();
//        i.forEach(System.out::println);
//        System.out.println(db.getById("2").get());
//        DBUtil.executor();
//        DBAutoRepository db = DBAutoRepository.getInstance();
//        List<Auto> aL = new ArrayList<>();
//        Auto.AutoBuilder a = new Auto.AutoBuilder();
//        a.buildManufacturer(Brand.AUDI);
//        a.buildCreated(LocalDateTime.now());
//        a.buildEngine(new Engine(4.5, Brand.AUDI, 8));
//        a.buildPrice(BigDecimal.valueOf(15000));
//        a.buildModel("A7");
//        a.buildBodyType(Type.SUV);
//        aL.add(a.getAuto());
//        Auto a1 = new Auto("Civic", BigDecimal.valueOf(9500), Brand.HONDA, Type.SEDAN, 2, 4);
//        aL.add(a1);
//        db.createList(aL);
//        db.create(a.getAuto());
//        db.create(a1);
//        db.clear();
//        db.delete("4dcc5a8a-4631-46de-8557-a4c32185fd0e");
//        List<Auto> autos = db.getAll();
//        autos.forEach(System.out::println);
//        System.out.println(db.getById("4dcc5a8a-4631-46de-8557-a4c32185fd0e"));
    }
}

