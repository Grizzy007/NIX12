package com.nix.module.entity;

import java.util.UUID;

public class Customer {
    private String id;
    private int age;
    private String email;

    public Customer() {
        this.id = UUID.randomUUID().toString();
    }

    public Customer(int age, String email) {
        this.id = UUID.randomUUID().toString();
        this.age = age;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer " +
                "id='" + id + '\'' +
                ", age=" + age +
                ", email='" + email;
    }
}
