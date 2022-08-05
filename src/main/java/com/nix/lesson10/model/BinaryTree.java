package com.nix.lesson10.model;

import com.nix.lesson10.model.comparator.PriceComparator;
import com.nix.lesson10.model.vehicle.Vehicle;

import java.util.Comparator;

public class BinaryTree<T extends Vehicle> {
    private static class Node<T extends Vehicle> {
        Node<T> left;
        Node<T> right;
        T value;

        protected Node(T value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    Node<T> root;

    public void add(T vehicle) {
        root = adding(root, vehicle);
    }

    public void print() {
        printing(root);
    }

    public int leftBranchSum() {
        return getPrice(root.left);
    }

    public int rightBranchSum() {
        return getPrice(root.right);
    }

    private int getPrice(Node<T> node) {
        if (node != null) {
            int left = getPrice(node.left);
            int price = node.value.getPrice().intValue();
            int right = getPrice(node.right);
            return price + left + right;
        }
        return 0;
    }

    private Node<T> adding(Node<T> current, T vehicle) {
        if (current == null) {
            return new Node<>(vehicle);
        }
        PriceComparator comparator = new PriceComparator();
        int compared = comparator.compare(current.value, vehicle);
        if (compared > 0) {
            current.right = adding(current.right, vehicle);
        } else if (compared < 0) {
            current.left = adding(current.left, vehicle);
        } else {
            return current;
        }
        return current;
    }

    private void printing(Node<T> node) {
        if (node != null) {
            printing(node.left);
            System.out.printf("Vehicle brand: %s, model: %s, price: %d%n",
                    node.value.getBrand(),
                    node.value.getModel(),
                    node.value.getPrice().intValue());
            printing(node.right);
        }
    }
}
