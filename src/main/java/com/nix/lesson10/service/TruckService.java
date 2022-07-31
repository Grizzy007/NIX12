package com.nix.lesson10.service;

import com.nix.lesson10.model.comparator.PriceComparator;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Truck;
import com.nix.lesson10.repository.TruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class TruckService extends VehicleService<Truck> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TruckService.class);

    private static TruckService instance;

    private TruckService(TruckRepository truckRepository) {
        super(truckRepository);
    }

    public static TruckService getInstance() {
        if (instance == null) {
            instance = new TruckService(TruckRepository.getInstance());
        }
        return instance;
    }

    @Override
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

    @Override
    public void update(BufferedReader reader) throws IOException {
        Truck[] trucks = repository.getAll().toArray(new Truck[0]);
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
        repository.update(trucks[index]);
    }

    @Override
    protected Truck createRandom() {
        return new Truck(
                "Model-" + RANDOM.nextInt(1000),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                getRandomManufacturer(),
                RANDOM.nextInt(100)
        );
    }

    @Override
    public void compare() {
        repository.getAll().sort(new PriceComparator()
                .thenComparing((o1, o2) -> o1.getModel().compareTo(o2.getModel()))
                .thenComparing((o1, o2) -> o1.getModel().length() - o2.getModel().length()));
        printAll();
    }

    public void priceStatistic() {
        DoubleSummaryStatistics cars = repository.getAll().stream()
                .mapToDouble(truck -> truck.getPrice().doubleValue())
                .summaryStatistics();
        System.out.printf("""
                    Average: %s
                    Sum: %s
                    Min: %s
                    Max: %s
                    Count: %s
                """, cars.getAverage(), cars.getSum(), cars.getMin(), cars.getMax(), cars.getCount());

    }

    public Truck pickTruckByCapacity(BufferedReader reader) throws IOException {
        System.out.println("Input needed capacity: ");
        int capacity = Integer.parseInt(reader.readLine());
        List<Truck> trucks = repository.getAll();
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
        if (truck.equals(optional)) {
            repository.create(truck.get());
        }
        return truck.get();
    }
}
