package com.arobs.model;

import java.util.ArrayList;

public class ShoppingCart {

    private int cartID;
    private ArrayList<Item> content;

    public ShoppingCart() {
        content = new ArrayList<>();
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
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
