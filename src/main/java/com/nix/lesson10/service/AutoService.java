package com.nix.lesson10.service;

import com.nix.lesson10.model.Auto;
import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Type;
import com.nix.lesson10.repository.AutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private static final AutoRepository AUTO_REPOSITORY = new AutoRepository();

    public List<Auto> createAutos(int count) {
        List<Auto> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Auto auto = new Auto(
                    "Model-" + RANDOM.nextInt(1000),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    getRandomManufacturer(),
                    getRandomBodyType()
            );
            result.add(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    public void saveAutos(List<Auto> autos) {
        AUTO_REPOSITORY.create(autos);
    }

    public void saveAuto(Auto auto) {
        AUTO_REPOSITORY.create(auto);
    }

    public Auto create(BufferedReader bf) throws IOException {
        System.out.print("Input model: ");
        String model = bf.readLine();
        System.out.print("Input brand from Uppercase(example, Toyota): ");
        String choice = bf.readLine();
        Brand brand = switch (choice) {
            case "Bmw" -> Brand.BMW;
            case "Volkswagen" -> Brand.VOLKSWAGEN;
            case "Audi" -> Brand.AUDI;
            case "Toyota" -> Brand.TOYOTA;
            case "Honda" -> Brand.HONDA;
            default -> null;
        };
        System.out.print("Input body type from Uppercase(example, Sedan): ");
        String type = bf.readLine();
        Type bodyType = switch (type) {
            case "Sedan" -> Type.SEDAN;
            case "Crossover" -> Type.CROSSOVER;
            case "Jeep" -> Type.JEEP;
            case "Suv" -> Type.SUV;
            default -> null;
        };
        System.out.print("Input price: ");
        double tempPrice = Double.parseDouble(bf.readLine());
        BigDecimal price = BigDecimal.valueOf(tempPrice);
        Auto auto = new Auto(model, price, brand, bodyType);
        LOGGER.debug("Created auto {}", auto.getId());
        return auto;
    }

    public void update(BufferedReader reader) throws IOException {
        Auto[] autos = AUTO_REPOSITORY.getAll().toArray(new Auto[0]);
        for (int i = 0; i < autos.length; i++) {
            System.out.println(i + ". " + autos[i].toString());
        }
        System.out.println("Input number of auto that needs a update: ");
        int index = Integer.parseInt(reader.readLine());
        System.out.println("Input new model: ");
        autos[index].setModel(reader.readLine());
        System.out.print("Input brand from Uppercase(example, Toyota): ");
        String choice = reader.readLine();
        autos[index].setBrand(switch (choice) {
            case "Bmw" -> Brand.BMW;
            case "Volkswagen" -> Brand.VOLKSWAGEN;
            case "Audi" -> Brand.AUDI;
            case "Toyota" -> Brand.TOYOTA;
            case "Honda" -> Brand.HONDA;
            default -> null;
        });
        System.out.print("Input body type from Uppercase(example, Sedan): ");
        String type = reader.readLine();
        autos[index].setBodyType(switch (type) {
            case "Sedan" -> Type.SEDAN;
            case "Crossover" -> Type.CROSSOVER;
            case "Jeep" -> Type.JEEP;
            case "Suv" -> Type.SUV;
            default -> null;
        });
        System.out.print("Input price: ");
        double tempPrice = Double.parseDouble(reader.readLine());
        BigDecimal price = BigDecimal.valueOf(tempPrice);
        autos[index].setPrice(price);
        AUTO_REPOSITORY.update(autos[index]);
    }

    public void delete(BufferedReader bf) throws IOException {
        Auto[] autos = AUTO_REPOSITORY.getAll().toArray(new Auto[0]);
        for (int i = 0; i < autos.length; i++) {
            System.out.println(i + ". " + autos[i].toString());
        }
        System.out.println("Input number of auto to delete: ");
        int index = Integer.parseInt(bf.readLine());
        String id = autos[index].getId();
        AUTO_REPOSITORY.delete(id);
        LOGGER.debug("Auto deleted {}", id);
    }

    public void printAll() {
        for (Auto auto : AUTO_REPOSITORY.getAll()) {
            System.out.println(auto);
        }
    }

    private Brand getRandomManufacturer() {
        final Brand[] values = Brand.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private Type getRandomBodyType() {
        final Type[] values = Type.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
