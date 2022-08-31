package com.nix.lesson10.service;

import com.nix.lesson10.annotations.Autowired;
import com.nix.lesson10.annotations.Singleton;
import com.nix.lesson10.model.Invoice;
import com.nix.lesson10.model.vehicle.Vehicle;
import com.nix.lesson10.repository.InvoiceRepository;
import com.nix.lesson10.repository.hibernate.HibernateInvoiceRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class InvoiceService {
    private final InvoiceRepository repository;
    private final AutoService autos;
    private final MotoService motos;
    private final TruckService trucks;

    private static final Random RANDOM = new Random();

    private static InvoiceService instance;

    @Autowired
    private InvoiceService() {
        repository = HibernateInvoiceRepository.getInstance();
        autos = AutoService.getInstance();
        motos = MotoService.getInstance();
        trucks = TruckService.getInstance();
    }

    public static synchronized InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService();
        }
        return instance;
    }

    public Invoice createWithNVehicles(int number) {
        Set<Vehicle> vehicles = new LinkedHashSet<>(number);
        for (int i = 0; i < number; i++) {
            vehicles.add(
                    getRandomVehicle());
        }
        Invoice invoice = new Invoice(LocalDateTime.now().minusHours(RANDOM.nextInt(1, 12)), vehicles);
        repository.create(invoice);
        return invoice;
    }

    public List<Invoice> getInvoicesMoreExpensiveThan(double price) {
        List<Invoice> invoices = repository.getAll();
        return invoices.stream()
                .filter(invoice -> invoice.getVehicles().stream()
                        .mapToDouble(v -> v.getPrice().doubleValue())
                        .sum() > price)
                .toList();
    }

    public int getCountOfInvoices() {
        return repository.getAll().size();
    }

    public Optional<Invoice> updateTimeOfInvoice(LocalDateTime time, String id) {
        Optional<Invoice> invoice = repository.getById(id);
        if (invoice.isPresent()) {
            Invoice toUpdate = invoice.get();
            toUpdate.setCreated(time);
            repository.update(toUpdate);
            return Optional.of(toUpdate);
        }
        return Optional.empty();
    }

    public void groupByPrice() {
        Map<Double, List<Invoice>> map = repository.getAll().stream()
                .collect(Collectors.groupingBy(price -> price.getVehicles().stream()
                        .mapToDouble(v -> v.getPrice().doubleValue())
                        .sum()));
        for (Double price : map.keySet()) {
            System.out.println("Price: " + price + " " + map.get(price));
        }
    }

    public String helperToUpdate(BufferedReader reader) throws IOException {
        List<Invoice> invoices = repository.getAll();
        for (int i = 0; i < invoices.size(); i++) {
            System.out.println(i + ". " + invoices.get(i).toString());
        }
        System.out.println("Input number of auto that you want to see a body type: ");
        int index = Integer.parseInt(reader.readLine());
        return invoices.get(index).getId();
    }

    private Vehicle getRandomVehicle() {
        int i = RANDOM.nextInt(1, 3);
        if (i == 1) {
            return autos.createRandom();
        } else if (i == 2) {
            return motos.createRandom();
        } else {
            return trucks.createRandom();
        }
    }
}
