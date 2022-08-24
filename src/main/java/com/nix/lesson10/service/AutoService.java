package com.nix.lesson10.service;

import com.nix.lesson10.annotations.Autowired;
import com.nix.lesson10.annotations.Singleton;
import com.nix.lesson10.model.comparator.PriceComparator;
import com.nix.lesson10.model.vehicle.*;
import com.nix.lesson10.repository.collection.AutoRepository;
import com.nix.lesson10.repository.CrudRepository;
import com.nix.lesson10.repository.db.DBAutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class AutoService extends VehicleService<Auto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static AutoService instance;

    @Autowired
    private AutoService(CrudRepository<Auto> repository) {
        super(repository);
    }

    public static AutoService getInstance() {
        if (instance == null) {
            instance = new AutoService(DBAutoRepository.getInstance());
        }
        return instance;
    }

    @Override
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
        Auto.AutoBuilder builder = new Auto.AutoBuilder();
        builder.buildModel(model);
        builder.buildPrice(price);
        builder.buildManufacturer(brand);
        builder.buildCreated(LocalDateTime.now());
        builder.buildBodyType(bodyType);
        builder.buildEngine(new Engine(1 + RANDOM.nextDouble(7),
                brand,
                1 + RANDOM.nextInt(7)));
        Auto auto = builder.getAuto();
        LOGGER.debug("Created auto {}", auto.getId());
        return auto;
    }

    @Override
    protected Auto createRandom() {
        Auto.AutoBuilder builder = new Auto.AutoBuilder();
        builder.buildModel("Model-" + RANDOM.nextInt(1000));
        builder.buildPrice(BigDecimal.valueOf(RANDOM.nextDouble(1000.0)));
        builder.buildManufacturer(getRandomManufacturer());
        builder.buildCreated(LocalDateTime.now());
        builder.buildBodyType(getRandomBodyType());
        builder.buildEngine(new Engine(RANDOM.nextInt(1,8),
                getRandomManufacturer(),
                1 + RANDOM.nextInt(7)));
        return builder.getAuto();
    }

    @Override
    public void update(BufferedReader reader) throws IOException {
        Auto[] autos = repository.getAll().toArray(new Auto[0]);
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
        repository.update(autos[index]);
    }

    @Override
    public void compare() {
        repository.getAll().sort(new PriceComparator()
                .thenComparing(Vehicle::getModel)
                .thenComparingInt(o1 -> o1.getModel().length()));
        printAll();
    }

    public void sortAndDistinct() {
        Map<String, Type> cars = repository.getAll().stream()
                .sorted((o1, o2) -> -o1.getModel().compareTo(o2.getModel()))
                .distinct()
                .collect(Collectors.toMap(key -> key.getId(), value -> value.getBodyType()));
        cars.forEach((key, val) -> System.out.printf("Key: %s Value: %s%n", key, val));
    }

    public boolean isCarsHave(String detail) {
        return repository.getAll().stream()
                .anyMatch(car -> car.getDetails().contains(detail));
    }

    public void saleOnAuto(BufferedReader bf) throws IOException {
        String id = getId(bf);
        Optional<Auto> forSale = repository.getById(id);
        forSale.filter((a) -> a.getPrice().intValue() > 500)
                .ifPresent((auto -> auto.setPrice(
                        auto.getPrice().multiply(BigDecimal.valueOf((0.5 + RANDOM.nextDouble(0.5)))))));
        Auto autoForSale = forSale.orElseThrow();
        repository.update(autoForSale);
    }

    public void getBodyType(BufferedReader bf) throws IOException {
        String id = getId(bf);
        Optional<Auto> getType = repository.getById(id);
        getType.map(Auto::getBodyType).ifPresent(System.out::println);
    }

}
