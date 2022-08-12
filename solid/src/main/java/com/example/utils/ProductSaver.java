package com.example.utils;

import com.example.model.NotifiableProduct;
import com.example.model.Product;
import com.example.model.ProductBundle;

import java.util.List;

public interface ProductSaver {
    void saveNotifiableProduct(NotifiableProduct product);
    void saveProductBundle(ProductBundle product);
    List<Product> getAllSaved();
}
