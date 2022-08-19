package com.nix.module;

import com.nix.module.entity.Invoice;
import com.nix.module.entity.Telephone;
import com.nix.module.entity.Television;
import com.nix.module.entity.Type;
import com.nix.module.service.ShopService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        ShopService service = ShopService.getInstance();
        service.setPriceLimit(1500);
        for (int i = 0; i < 15; i++) {
            service.getRandomInvoice();
        }
        List<Invoice> invoices = service.getAllInvoices();
        int soldTelephones = invoices.stream()
                .mapToInt(i-> (int) i.getElectronics().stream()
                        .filter(Telephone.class::isInstance)
                        .count()).sum();
        int soldTelevisions = invoices.stream()
                .mapToInt(i-> (int) i.getElectronics().stream()
                        .filter(Television.class::isInstance)
                        .count()).sum();
        System.out.printf("Number of sold telephones => %s%nNumber of sold televisions=> %s%n",
                soldTelephones,soldTelevisions);
        Invoice minSum = invoices.stream()
                        .min((p1,p2)->p1.getElectronics().stream()
                                .mapToInt(p -> p.getPrice().intValue())
                                .sum() - p2.getElectronics().stream()
                                .mapToInt(p -> p.getPrice().intValue())
                                .sum()).orElseThrow();
        System.out.printf("Minimal sum of invoice prices => %d%n And this invoice => %s%n",
                minSum.getElectronics().stream().mapToInt(p -> p.getPrice().intValue())
                .sum(),minSum);
        double sumOfAll = invoices.stream()
                .mapToDouble(i -> i.getElectronics().stream()
                        .mapToDouble((p -> p.getPrice().doubleValue()))
                        .sum())
                .sum();
        System.out.printf("Sum of prices of all invoices => %f%n", sumOfAll);
        System.out.println("Count of retail invoices => "
                + invoices.stream().filter(i -> i.getType().equals(Type.RETAIl)).count());
        System.out.println("================Invoices with only one type================");
        invoices.stream()
                .filter(i -> i.getElectronics().stream()
                        .allMatch(Telephone.class::isInstance))
                .forEach(System.out::println);
        invoices.stream()
                .filter(i -> i.getElectronics().stream()
                        .allMatch(Television.class::isInstance))
                .forEach(System.out::println);
        System.out.println("First 3 invoices -> ");
        invoices.stream().sorted(Comparator.comparing(Invoice::getCreated)).limit(3).forEach(System.out::println);
        System.out.println("Invoices customers less then 18 years -> ");
        invoices.stream().filter(i -> i.getCustomer().getAge() < 18).forEach(System.out::println);
        System.out.println("Sorted invoices -> ");
        CustomerAgeComparator comparator = new CustomerAgeComparator();
        invoices.sort(comparator
                .thenComparing((i1, i2) -> (int) (i1.getElectronics().stream().count() - i2.getElectronics().stream().count()))
                .thenComparing((i1, i2) -> i1.getElectronics().stream().mapToInt(p -> p.getPrice().intValue()).sum()
                        - i2.getElectronics().stream().mapToInt(p -> p.getPrice().intValue())
                        .sum()));
        service.printInvoices();
    }
}
