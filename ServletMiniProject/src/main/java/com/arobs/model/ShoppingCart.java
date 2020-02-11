package com.arobs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private ArrayList<Order> content;

    public ShoppingCart() {
        content = new ArrayList<>();
    }

    public ShoppingCart(ArrayList<Order> content) {
        this.content = content;
    }

    public ArrayList<Order> getContent() {
        return content;
    }

    public void setContent(ArrayList<Order> content) {
        this.content = content;
    }
}
