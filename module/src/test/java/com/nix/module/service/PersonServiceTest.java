package com.nix.module.service;

import com.nix.module.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
    private PersonService target;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        target = PersonService.getInstance();
    }

    @Test
    void generateRandom() {
        customer = target.generateRandom();
        Customer c1 = customer;
        assertNotNull(target.generateRandom());
        assertNotNull(customer);
        assertEquals(customer, c1);
    }
}