package com.nix.lesson10.repository;

import com.nix.lesson10.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    List<Invoice> getAll();

    Optional<Invoice> getById(String id);


    Invoice create(Invoice invoice);

    boolean createList(List<Invoice> list);

    boolean update(Invoice invoice);

    Invoice delete(String id);
}
