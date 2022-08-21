package com.nix.module.service;

import com.nix.module.entity.Customer;

import java.util.Random;

public class PersonService {
    private static final Random RANDOM = new Random();

    private static PersonService instance;

    public static PersonService getInstance(){
        if(instance == null)
        {
            instance = new PersonService();
        }
        return instance;
    }

    public Customer generateRandom() {
        Customer customer = new Customer();
        customer.setAge(RANDOM.nextInt(10,99));
        customer.setEmail("customer"+RANDOM.nextInt(31)+RANDOM.nextInt(12)+"@gmail.com");
        return customer;
    }
}
