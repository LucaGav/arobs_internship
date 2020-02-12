package com.arobs.model;

public class Item {

    private Product product;
    private int storageAmount;
    private boolean isGlobal;

    public Item() {
    }

    public Item(Product product, int storageAmount, boolean isGlobal) {
        this.product = product;
        this.storageAmount = storageAmount;
        this.isGlobal = isGlobal;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
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
