package com.nix.lesson10.ui;

import com.nix.lesson10.service.InvoiceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class DBCommand implements Command {
    private static final InvoiceService SERVICE = InvoiceService.getInstance();

    @Override
    public void execute(BufferedReader reader) throws IOException, URISyntaxException {
        System.out.println("""
                1) Create invoice with N vehicles;
                2) Get invoices more expensive than N;
                3) Get count of invoices;
                4) Update time of invoices;
                5) Group invoices by price;
                """);
        int choice = Integer.parseInt(reader.readLine());
        switch (choice){
            case 1 -> {
                System.out.println("Input number of vehicles add to invoice => ");
                int number = Integer.parseInt(reader.readLine());
                System.out.println(SERVICE.createWithNVehicles(number));
            }
            case 2 -> {
                System.out.println("Input price  to compare with => ");
                double price = Integer.parseInt(reader.readLine());
                System.out.println(SERVICE.getInvoicesMoreExpensiveThan(price));
            }
            case 3 -> System.out.printf("Invoices in database - %s%n",SERVICE.getCountOfInvoices());
            case 4 -> System.out.println(SERVICE.updateTimeOfInvoice(LocalDateTime.now(),
                    SERVICE.helperToUpdate(reader)));
            case 5 -> SERVICE.groupByPrice();
        }
    }
}
