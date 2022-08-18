package com.nix.module.service;

import com.nix.module.entity.Invoice;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public interface InvoiceGenerator {
    Optional<Invoice> getRandomInvoice() throws URISyntaxException, IOException;
}
