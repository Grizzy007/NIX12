package com.nix.lesson10.service;

import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Motorcycle;
import com.nix.lesson10.repository.MotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MotoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MotoService.class);
    private static final Random RANDOM = new Random();
    private final MotoRepository MOTO_REPOSITORY;

    public MotoService(MotoRepository motoRepository){
        MOTO_REPOSITORY = motoRepository;
    }

    public List<Motorcycle> createMotos(int count) {
        List<Motorcycle> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Motorcycle moto = new Motorcycle(
                    "Model-" + RANDOM.nextInt(1000),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    getRandomManufacturer(),
                    RANDOM.nextInt(100)
            );
            result.add(moto);
            LOGGER.debug("Created motorcycle {}", moto.getId());
        }
        return result;
    }

    public boolean saveMotos(List<Motorcycle> motos) {
        MOTO_REPOSITORY.createList(motos);
        return true;
    }

    public void saveMoto(Motorcycle moto) {
        MOTO_REPOSITORY.create(moto);
    }

    public Motorcycle create(BufferedReader bf) throws IOException {
        System.out.print("Input model: ");
        String model = bf.readLine();
        System.out.print("Input brand from Uppercase(example, Toyota):");
        String choice = bf.readLine();
        Brand brand = switch (choice) {
            case "Bmw" -> Brand.BMW;
            case "Volkswagen" -> Brand.VOLKSWAGEN;
            case "Audi" -> Brand.AUDI;
            case "Toyota" -> Brand.TOYOTA;
            case "Honda" -> Brand.HONDA;
            default -> null;
        };
        System.out.print("Input landing(in cm): ");
        int landing = Integer.parseInt(bf.readLine());
        System.out.print("Input price: ");
        double tempPrice = Double.parseDouble(bf.readLine());
        BigDecimal price = BigDecimal.valueOf(tempPrice);
        Motorcycle moto = new Motorcycle(model, price, brand, landing);
        LOGGER.debug("Created motorcycle {}", moto.getId());
        return moto;
    }

    public void update(BufferedReader reader) throws IOException {
        Motorcycle[] motorcycles = MOTO_REPOSITORY.getAll().toArray(new Motorcycle[0]);
        for (int i = 0; i < motorcycles.length; i++) {
            System.out.println(i + ". " + motorcycles[i].toString());
        }
        System.out.println("Input number of motorcycle that needs a update: ");
        int index = Integer.parseInt(reader.readLine());
        System.out.println("Input new model: ");
        motorcycles[index].setModel(reader.readLine());
        System.out.print("Input brand from Uppercase(example, Toyota): ");
        String choice = reader.readLine();
        motorcycles[index].setBrand(switch (choice) {
            case "Bmw" -> Brand.BMW;
            case "Volkswagen" -> Brand.VOLKSWAGEN;
            case "Audi" -> Brand.AUDI;
            case "Toyota" -> Brand.TOYOTA;
            case "Honda" -> Brand.HONDA;
            default -> null;
        });
        System.out.print("Input landing(in cm): ");
        motorcycles[index].setLanding(Integer.parseInt(reader.readLine()));
        System.out.print("Input price: ");
        double tempPrice = Double.parseDouble(reader.readLine());
        BigDecimal price = BigDecimal.valueOf(tempPrice);
        motorcycles[index].setPrice(price);
        MOTO_REPOSITORY.update(motorcycles[index]);
    }

    public void delete(BufferedReader bf) throws IOException {
        String id = getId(bf);
        MOTO_REPOSITORY.delete(id);
        LOGGER.debug("Motorcycle deleted {}", id);
    }

    public void getMotoByPrice(BufferedReader reader) throws IOException {
        System.out.println("Input price that you ready to spend on moto(minimum = 100$):");
        double price = Double.parseDouble(reader.readLine());
        if(price<100){
            System.out.println("Incorrect price");
            return;
        }
        List<Motorcycle> list = MOTO_REPOSITORY.getAll();
        Motorcycle toCreate = new Motorcycle("Model " + RANDOM.nextInt(100),
                BigDecimal.valueOf(price),
                getRandomManufacturer(),
                RANDOM.nextInt(120));
        Motorcycle motorcycle = list.stream()
                .filter(moto -> moto.getPrice().equals(BigDecimal.valueOf(price)))
                .findAny()
                .orElse(toCreate);
        if (motorcycle.equals(toCreate)) {
            MOTO_REPOSITORY.create(toCreate);
        }
        System.out.println("Here is your motorcycle by price: " + motorcycle);
    }

    public void printAll() {
        for (Motorcycle motorcycle : MOTO_REPOSITORY.getAll()) {
            System.out.println(motorcycle);
        }
    }

    private Brand getRandomManufacturer() {
        final Brand[] values = Brand.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private String getId(BufferedReader reader) throws IOException {
        Motorcycle[] motorcycles = MOTO_REPOSITORY.getAll().toArray(new Motorcycle[0]);
        for (int i = 0; i < motorcycles.length; i++) {
            System.out.println(i + ". " + motorcycles[i].toString());
        }
        System.out.println("Input number of motorcycle to delete: ");
        int index = Integer.parseInt(reader.readLine());
        return motorcycles[index].getId();
    }
}
