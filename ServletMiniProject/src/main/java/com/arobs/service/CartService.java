package com.arobs.service;

import com.arobs.model.Order;
import com.arobs.model.Product;
import com.arobs.model.ShoppingCart;
import com.sun.scenario.effect.impl.prism.PrDrawable;

import java.util.ArrayList;

public class CartService {

    public static void checkIfExists(ArrayList<Order> orders, Order o){
        int ok = 0;
        for(Order oo: orders){
            if(oo.getProduct().getType().equals(o.getProduct().getType())){
                oo.setStorageAmount(oo.getStorageAmount() + o.getStorageAmount());
                ok = 1;
            }
        }
        if(ok==0){
            orders.add(o);
        }
    }

    public static int removeProduct(ShoppingCart s, String prType){
        for(Order o : s.getContent()){
            if(o.getProduct().getType().equals(prType)){
                int amount = o.getStorageAmount();
                s.getContent().remove(o);
                return amount;
            }
        }
        return 0;
    }

}
