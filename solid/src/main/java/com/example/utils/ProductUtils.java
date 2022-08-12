package com.example.utils;


import com.example.model.NotifiableProduct;
import com.example.model.Product;
import com.example.model.ProductBundle;
import com.example.repository.ProductRepository;

import java.util.List;
import java.util.Random;

public class ProductUtils implements ProductGenerator, ProductSaver, ProductNotificator{
    private static final Random RANDOM = new Random();
    private final ProductRepository repository;

    public ProductUtils(){
         repository = ProductRepository.getInstance();
    }


    public void saveNotifiableProduct(NotifiableProduct product) {
        repository.save(product);
    }

    public void saveProductBundle(ProductBundle product) {
        repository.save(product);
    }

    public int filterNotifiableProductsAndSendNotifications() {
        int notifications = 0;
        List<NotifiableProduct> products = repository.getAll().stream()
                .filter(NotifiableProduct.class::isInstance)
                .map(NotifiableProduct.class::cast).toList();
        System.out.println(products.size());
        return notifications;
    }

    public List<Product> getAllSaved() {
        return repository.getAll();
    }

    public Product generateRandomProduct() {
        if (RANDOM.nextBoolean()) {
            ProductBundle productBundle = new ProductBundle();
            productBundle.setAmount(RANDOM.nextInt(15));
            productBundle.setAvailable(RANDOM.nextBoolean());
            productBundle.setChannel(RANDOM.nextBoolean() + "" + RANDOM.nextDouble());
            productBundle.setPrice(RANDOM.nextDouble());
            productBundle.setId(RANDOM.nextLong());
            productBundle.setTitle(RANDOM.nextFloat() + "" + RANDOM.nextDouble());
            return productBundle;
        } else {
            NotifiableProduct product = new NotifiableProduct();
            product.setId(RANDOM.nextLong());
            product.setTitle(RANDOM.nextFloat() + "" + RANDOM.nextDouble());
            product.setAvailable(RANDOM.nextBoolean());
            product.setChannel(RANDOM.nextBoolean() + "" + RANDOM.nextDouble());
            product.setPrice(RANDOM.nextDouble());
            return product;
        }
    }
}