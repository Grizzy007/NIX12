package com.nix.lesson10.service;

import com.nix.lesson10.model.Brand;
import com.nix.lesson10.model.Truck;
import com.nix.lesson10.repository.TruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TruckService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TruckService.class);
    private static final Random RANDOM = new Random();
    private static final TruckRepository TRUCK_REPOSITORY = new TruckRepository();

    public List<Truck> createTrucks(int count) {
        List<Truck> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Truck truck = new Truck(
                    "Model-" + RANDOM.nextInt(1000),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    getRandomManufacturer(),
                    RANDOM.nextInt(100)
            );
            result.add(truck);
            LOGGER.debug("Created truck {}", truck.getId());
        }
        return result;
    }

    public void saveTrucks(List<Truck> trucks) {
        TRUCK_REPOSITORY.create(trucks);
    }

    public void saveTruck(Truck truck) {
        TRUCK_REPOSITORY.create(truck);
    }

    public Truck create(BufferedReader bf) throws IOException {
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
        System.out.print("Input capacity(in tons): ");
        int landing = Integer.parseInt(bf.readLine());
        System.out.print("Input price: ");
        double tempPrice = Double.parseDouble(bf.readLine());
        BigDecimal price = BigDecimal.valueOf(tempPrice);
        Truck truck = new Truck(model, price, brand, landing);
        LOGGER.debug("Created truck {}", truck.getId());
        return truck;
    }

    public void update(BufferedReader reader) throws IOException {
        Truck[] trucks = TRUCK_REPOSITORY.getAll().toArray(new Truck[0]);
        for (int i = 0; i < trucks.length; i++) {
            System.out.println(i + ". " + trucks[i].toString());
        }
        System.out.println("Input number of auto that needs a update: ");
        int index = Integer.parseInt(reader.readLine());
        System.out.println("Input new model: ");
        trucks[index].setModel(reader.readLine());
        System.out.print("Input brand from Uppercase(example, Toyota): ");
        String choice = reader.readLine();
        trucks[index].setBrand(switch (choice) {
            case "Bmw" -> Brand.BMW;
            case "Volkswagen" -> Brand.VOLKSWAGEN;
            case "Audi" -> Brand.AUDI;
            case "Toyota" -> Brand.TOYOTA;
            case "Honda" -> Brand.HONDA;
            default -> null;
        });
        System.out.print("Input capacity(in tons): ");
        trucks[index].setCapacity(Integer.parseInt(reader.readLine()));
        System.out.print("Input price: ");
        double tempPrice = Double.parseDouble(reader.readLine());
        BigDecimal price = BigDecimal.valueOf(tempPrice);
        trucks[index].setPrice(price);
        TRUCK_REPOSITORY.update(trucks[index]);
    }

    public void delete(BufferedReader bf) throws IOException {
        Truck[] trucks = TRUCK_REPOSITORY.getAll().toArray(new Truck[0]);
        for (int i = 0; i < trucks.length; i++) {
            System.out.println(i + ". " + trucks[i].toString());
        }
        System.out.println("Input number of truck to delete: ");
        int index = Integer.parseInt(bf.readLine());
        String id = trucks[index].getId();
        TRUCK_REPOSITORY.delete(id);
        LOGGER.debug("Truck deleted {}", id);
    }

    public void pickTruckByCapacity(BufferedReader reader) throws IOException {
        System.out.println("Input needed capacity: ");
        int capacity = Integer.parseInt(reader.readLine());
        List<Truck> trucks = TRUCK_REPOSITORY.getAll();
        Optional<Truck> optional = Optional.of(
                new Truck(
                        "Special model",
                        BigDecimal.valueOf(RANDOM.nextInt(1000)),
                        getRandomManufacturer(),
                        capacity)
        );
        Optional<Truck> truck = trucks.stream()
                .filter((t) -> t.getCapacity() == capacity)
                .findAny()
                .or(() -> optional);
        if(truck.equals(optional)){
            TRUCK_REPOSITORY.create(truck.get());
        }
    }

    public void printAll() {
        for (Truck truck : TRUCK_REPOSITORY.getAll()) {
            System.out.println(truck);
        }
    }

    private Brand getRandomManufacturer() {
        final Brand[] values = Brand.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
