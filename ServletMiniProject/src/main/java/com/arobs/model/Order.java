package com.arobs.model;

public class Order {
    private Product product;
    private int storageAmount;

    public Order() {
    }

    public Order(Product product, int storageAmount) {
        this.product = product;
        this.storageAmount = storageAmount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
    }
}
