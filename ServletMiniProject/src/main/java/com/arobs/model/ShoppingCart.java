package com.arobs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private ArrayList<Product> content;

    public ShoppingCart() {
        content = new ArrayList<>();
    }

    public ShoppingCart(ArrayList<Product> content) {
        this.content = content;
    }

    public ArrayList<Product> getContent() {
        return content;
    }

    public void setContent(ArrayList<Product> content) {
        this.content = content;
    }
}
