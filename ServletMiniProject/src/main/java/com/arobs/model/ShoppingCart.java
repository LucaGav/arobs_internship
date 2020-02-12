package com.arobs.model;

import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<Item> content;

    public ShoppingCart() {
        content = new ArrayList<>();
    }

    public ShoppingCart(ArrayList<Item> content) {
        this.content = content;
    }

    public ArrayList<Item> getContent() {
        return content;
    }

    public void setContent(ArrayList<Item> content) {
        this.content = content;
    }
}
