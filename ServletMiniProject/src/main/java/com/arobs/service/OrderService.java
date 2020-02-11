package com.arobs.service;

import com.arobs.model.Order;
import com.arobs.model.Product;
import com.arobs.model.User;

import java.util.ArrayList;

public class OrderService {
    private static ArrayList<Order>  orders = new ArrayList<>();

    public static ArrayList<Order> getAvailableOrders() {
        return orders;
    }

    public static Order getOrder(int id){
        Product p = new Product();
        switch(id){
            case 1:
                p.setType("Mar");
                return new Order(p,23);
            case 2:
                p.setType("Par");
                return new Order(p,13);
            case 3:
                p.setType("Dar");
                return new Order(p,15);
            case 4:
                p.setType("Zar");
                return new Order(p,16);
            default:
                return null;
        }
    }

    public static void addOrders(){
        for(int i = 1 ; i <=4 ; i++){
            orders.add(getOrder(i));
        }
    }
    public static void addOrder(Order order){
        orders.add(order);
    }

    public static ArrayList<Order> updateGlobalOrders(String flag, ArrayList<Order> orders, String productType, int amount) {
        if (flag.equals("add")) {
            for (Order o : orders) {
                if (o.getProduct().getType().equals(productType)) {
                    o.setStorageAmount(o.getStorageAmount() - amount);
                }
                break;
            }
            return orders;
        } else {
            for (Order o : orders) {
                if (o.getProduct().getType().equals(productType)) {
                    o.setStorageAmount(o.getStorageAmount() + amount);
                }
            }
            return orders;
        }
    }
}
