package com.arobs.model;

public class User {


    private int userID;
    private String username;
    private String password;
    private ShoppingCart cart;

    public User() {
    }

    public User(String username, String password, ShoppingCart cart) {
        this.username = username;
        this.password = password;
        this.cart = cart;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
