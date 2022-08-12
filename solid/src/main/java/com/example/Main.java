package com.example;

import com.example.model.NotifiableProduct;
import com.example.model.Product;
import com.example.model.ProductBundle;
import com.example.utils.ProductUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductUtils utils = new ProductUtils();
        List<Product> products = new ArrayList<>();
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.add(utils.generateRandomProduct());
        products.forEach(it -> {
            if (it instanceof ProductBundle bundle) {
                utils.saveProductBundle(bundle);
            } else if (it instanceof NotifiableProduct product) {
                utils.saveNotifiableProduct(product);
            }
        });

        System.out.println(utils.getAllSaved());
        System.out.println("notifications sent: " + utils.filterNotifiableProductsAndSendNotifications());
    }
}