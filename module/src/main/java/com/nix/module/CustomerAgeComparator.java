package com.nix.module;

import com.nix.module.entity.Invoice;

import java.util.Comparator;

public class CustomerAgeComparator implements Comparator<Invoice> {
    @Override
    public int compare(Invoice o1, Invoice o2) {
        return -o1.getCustomer().getAge() + o2.getCustomer().getAge();
    }
}