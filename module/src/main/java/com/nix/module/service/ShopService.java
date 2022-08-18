package com.nix.module.service;

import com.nix.module.entity.*;
import com.nix.module.exceptions.IncorrectStringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class ShopService implements FileReader, InvoiceGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);
    private static final Random RANDOM = new Random();

    private final List<Invoice> invoiceList;
    private BigDecimal priceLimit;
    private final PersonService service;
    private static ShopService instance;


    public static ShopService getInstance() {
        if (instance == null) {
            instance = new ShopService();
        }
        return instance;
    }

    private ShopService() {
        invoiceList = new ArrayList<>();
        service = PersonService.getInstance();
    }

    public void setPriceLimit(int price) {
        System.out.println("Please set price limit: ");
        priceLimit = BigDecimal.valueOf(price);
    }

    @Override
    public Optional<Invoice> getRandomInvoice() throws URISyntaxException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = Paths.get(Objects.requireNonNull(classLoader.getResource("products.csv")).toURI()).toFile();
        if (file.isDirectory() || !file.exists()) {
            return Optional.empty();
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)))) {
            String header = reader.readLine();
            if (header == null) {
                throw new IllegalArgumentException();
            }
            int typeIndex = findType(header).orElse(0);
            List<String[]> products = readFile(reader);
            List<Electronics> electronics = products.stream().map(m -> {
                if (m[typeIndex].equals("Television")) {
                    return getTelevision(header, m);
                } else {
                    return getTelephone(header, m);
                }
            }).toList();
            Invoice invoice = invoiceBuilder(electronics);
            invoiceList.add(invoice);
            LOGGER.debug("Invoice created {} {}", invoice.getCustomer(), invoice);
            return Optional.of(invoice);
        } catch (FileNotFoundException | IncorrectStringException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<String[]> readFile(BufferedReader reader) throws IOException, IncorrectStringException {
        String product;
        List<String[]> products = new ArrayList<>();
        while ((product = reader.readLine()) != null) {
            if (product.contains(",,")) {
                throw new IncorrectStringException(product);
            }
            products.add(product.split(","));
        }
        return products;
    }

    public void printInvoices() {
        invoiceList.forEach(i -> System.out.println(i));
    }

    public List<Invoice> getAllInvoices() {
        return invoiceList;
    }

    private Telephone getTelephone(String header, String[] values) {
        Map<String, String> map = stringsToMap(header, values);
        Function<Map<String, String>, Telephone> f = m ->
                new Telephone(m.get("series"), Screen.valueOf(m.get("screen type")),
                        BigDecimal.valueOf(Double.parseDouble(m.get("price"))), m.get("model"));
        return f.apply(map);
    }

    private Television getTelevision(String header, String[] values) {
        Map<String, String> map = stringsToMap(header, values);
        Function<Map<String, String>, Television> f = m ->
                new Television(m.get("series"), Screen.valueOf(m.get("screen type")),
                        BigDecimal.valueOf(Double.parseDouble(m.get("price"))),
                        Double.parseDouble(m.get("diagonal")), m.get("country"));
        return f.apply(map);
    }

    private Map<String, String> stringsToMap(String header, String[] values) {
        String[] headerValues = header.split(",");
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < headerValues.length; i++) {
            map.put(headerValues[i], values[i]);
        }
        return map;
    }

    private Optional<Integer> findType(String header) {
        String[] headerValues = header.split(",");
        for (int i = 0; i < headerValues.length; i++) {
            if (headerValues[i] == "type") {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    private Invoice invoiceBuilder(List<Electronics> electronics) {
        Invoice invoice = new Invoice();
        invoice.setCreated(LocalDateTime.now().minusHours(RANDOM.nextInt(24)));
        invoice.setCustomer(service.generateRandom());
        List<Electronics> toInvoice = new ArrayList<>();
        int k = RANDOM.nextInt(1, 5);
        for (int i = 0; i < k; i++) {
            toInvoice.add(electronics.get(RANDOM.nextInt(electronics.size())));
        }
        invoice.setElectronics(toInvoice);
        invoice.setType(invoice.getElectronics().stream()
                .mapToInt(price -> price.getPrice().intValue())
                .sum() > priceLimit.intValue() ? Type.WHOLESALE : Type.RETAIl);
        return invoice;
    }
}
