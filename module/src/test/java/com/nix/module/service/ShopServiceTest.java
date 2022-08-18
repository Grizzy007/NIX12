package com.nix.module.service;

import com.nix.module.entity.Customer;
import com.nix.module.entity.Invoice;
import com.nix.module.exceptions.IncorrectStringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ShopService target;
    private PersonService service;
    private Customer customer;
    private Invoice invoice;

    @BeforeEach
    void setUp() {
        target = ShopService.getInstance();
        service = PersonService.getInstance();
        customer = service.generateRandom();
        target.setPriceLimit(1200);
    }

    @Test
    void getAllInvoices() throws URISyntaxException, IOException {
        target.getRandomInvoice();
        target.getRandomInvoice();
        target.getRandomInvoice();
        assertEquals(target.getAllInvoices(),target.getAllInvoices());
    }

    @Test
    void setPriceLimit() throws IOException, URISyntaxException {
        target.setPriceLimit(1500);
        assertNotNull(target.getRandomInvoice());
    }

    @Test
    void getRandomInvoice() throws URISyntaxException, IOException {
        invoice = target.getRandomInvoice().get();
        assertNotNull(invoice);
        assertNotEquals(invoice,target.getRandomInvoice());
    }

    @Test
    void printAll() throws URISyntaxException, IOException {
        ShopService s = Mockito.mock(ShopService.class);
        List<Invoice> invoices = List.of(target.getRandomInvoice().get(), target.getRandomInvoice().get());
        Mockito.when(s.getAllInvoices()).thenReturn(invoices);
        target.printInvoices();
    }
}