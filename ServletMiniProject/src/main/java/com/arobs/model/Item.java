package com.arobs.model;

public class Item {

    private int itemID;
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

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
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
