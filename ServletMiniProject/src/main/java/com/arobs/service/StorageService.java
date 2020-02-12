package com.arobs.service;

import com.arobs.model.Item;
import com.arobs.model.Product;

import java.util.ArrayList;

public class StorageService {
    private static ArrayList<Item> items = new ArrayList<>();

    public static ArrayList<Item> getAvailableOrders() {
        return items;
    }

    public static Item getOrder(int id){
        Product p = new Product();
        switch(id){
            case 1:
                p.setType("Mar");
                return new Item(p,23,true);
            case 2:
                p.setType("Par");
                return new Item(p,13,true);
            case 3:
                p.setType("Dar");
                return new Item(p,15,true);
            case 4:
                p.setType("Zar");
                return new Item(p,16,true);
            default:
                return null;
        }
    }

    public static void addOrders(){
        for(int i = 1 ; i <=4 ; i++){
            items.add(getOrder(i));
        }
    }
    public static void addOrder(Item item){
        items.add(item);
    }

    public static ArrayList<Item> updateGlobalOrders(String flag, ArrayList<Item> items, String productType, int amount) {
        if (flag.equals("add")) {
            for (Item o : items) {
                if (o.getProduct().getType().equals(productType)) {
                    o.setStorageAmount(o.getStorageAmount() - amount);
                }
                break;
            }
            return items;
        } else {
            for (Item o : items) {
                if (o.getProduct().getType().equals(productType)) {
                    o.setStorageAmount(o.getStorageAmount() + amount);
                }
            }
            return items;
        }
    }
}
