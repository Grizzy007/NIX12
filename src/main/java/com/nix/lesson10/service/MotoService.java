package com.nix.lesson10.service;

import com.nix.lesson10.annotations.Autowired;
import com.nix.lesson10.annotations.Singleton;
import com.nix.lesson10.model.comparator.PriceComparator;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Motorcycle;
import com.nix.lesson10.repository.CrudRepository;
import com.nix.lesson10.repository.collection.MotoRepository;
import com.nix.lesson10.repository.db.DBMotoRepository;
import com.nix.lesson10.repository.hibernate.HibernateMotoRepository;
import com.nix.lesson10.repository.nosql.MongoMotoRepository;
import com.nix.lesson10.util.MongoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Singleton
public class MotoService extends VehicleService<Motorcycle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MotoService.class);

    private static MotoService instance;

    @Autowired
    private MotoService(CrudRepository<Motorcycle> motoRepository) {
        super(motoRepository);
    }

    public static MotoService getInstance(){
        if(instance==null){
            instance = new MotoService(HibernateMotoRepository.getInstance());
        }
        return instance;
    }

    @Override
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
        Motorcycle moto = new Motorcycle(model, price, brand, landing, 1 + RANDOM.nextDouble(4),
                1 + RANDOM.nextInt(6));
        LOGGER.debug("Created motorcycle {}", moto.getId());
        return moto;
    }

    @Override
    protected Motorcycle createRandom() {
        return new Motorcycle(
                "Model-" + RANDOM.nextInt(1000),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                getRandomManufacturer(),
                RANDOM.nextInt(150),
                RANDOM.nextInt(1,6),
                RANDOM.nextInt(1,6)
        );
    }

    @Override
    public void update(BufferedReader reader) throws IOException {
        Motorcycle[] motorcycles = repository.getAll().toArray(new Motorcycle[0]);
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
        repository.update(motorcycles[index]);
    }

    @Override
    public void compare(){
        repository.getAll().sort(new PriceComparator()
                .thenComparing((o1, o2) -> o1.getModel().compareTo(o2.getModel()))
                .thenComparing((o1, o2) -> o1.getModel().length() - o2.getModel().length()));
        printAll();
    }

    public boolean priceCheck(List<Motorcycle> motos){//Predicate realisation 2
        for(Motorcycle m: motos){
            if(m.getPrice().equals(null)){
                return false;
            }
        }
        return true;
    }

    public boolean checkPrice(){//Predicate realisation 1
        return repository.getAll().stream()
                .allMatch(motorcycle -> motorcycle.getPrice().intValue() > 0);
    }

    public boolean checkRepo(){
        return priceCheck(repository.getAll());
    }

    public void getMotoByPrice(BufferedReader reader) throws IOException {
        System.out.println("Input price that you ready to spend on moto(minimum = 100$):");
        double price = Double.parseDouble(reader.readLine());
        if (price < 100) {
            System.out.println("Incorrect price");
            return;
        }
        List<Motorcycle> list = repository.getAll();
        Motorcycle toCreate = new Motorcycle("Model " + RANDOM.nextInt(100),
                BigDecimal.valueOf(price),
                getRandomManufacturer(),
                RANDOM.nextInt(120),
                RANDOM.nextDouble(6),
                RANDOM.nextInt(6));
        Motorcycle motorcycle = list.stream()
                .filter(moto -> moto.getPrice().equals(BigDecimal.valueOf(price)))
                .findAny()
                .orElse(toCreate);
        if (motorcycle.equals(toCreate)) {
            repository.create(toCreate);
        }
        System.out.println("Here is your motorcycle by price: " + motorcycle);
    }

}
