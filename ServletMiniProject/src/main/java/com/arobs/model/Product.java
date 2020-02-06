package com.arobs.model;

public class Product {
    private String type;
    private int storageAmount;
    public Product() {
    }

    public Product(String type, int storageAmount) {
        this.type = type;
        this.storageAmount = storageAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
    }
}
