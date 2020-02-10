package com.arobs.service;

import com.arobs.model.Product;
import com.arobs.model.User;

import java.util.ArrayList;

public class ProductService {
    private static ArrayList<Product> products = new ArrayList<>();

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static Product getProduct(int id){
        switch(id){
            case 1:
                return new Product("Mar",23);
            case 2:
                return new Product("Par",13);
            case 3:
                return new Product("Dar",15);
            case 4:
                return new Product("Zar",16);
            default:
                return null;
        }
    }

    public static void addProducts(){
        for(int i = 1 ; i <=4 ; i++){
            products.add(getProduct(i));
        }
    }
    public static void addProduct(Product product){
        products.add(product);
    }

    public static ArrayList<Product> updateGlobalProducts(String flag, ArrayList<Product> products, String product, int amount) {
        if (flag.equals("add")) {
            for (Product p : products) {
                if (p.getType().equals(product)) {
                    p.setStorageAmount(p.getStorageAmount() - amount);
                }
                break;
            }
            return products;
        } else {
            for (Product p : products) {
                if (p.getType().equals(product)) {
                    p.setStorageAmount(p.getStorageAmount() + amount);
                }
            }
            return products;
        }
    }
}
