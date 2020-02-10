package com.arobs.service;

import com.arobs.model.Product;
import com.arobs.model.ShoppingCart;
import com.sun.scenario.effect.impl.prism.PrDrawable;

import java.util.ArrayList;

public class CartService {

    public static void checkIfExists(ArrayList<Product> products, Product p){
        int ok = 0;
        for(Product pp: products){
            if(pp.getType().equals(p.getType())){
                pp.setStorageAmount(pp.getStorageAmount() + p.getStorageAmount());
                ok = 1;
            }
        }
        if(ok==0){
            products.add(p);
        }
    }

    public static int removeProduct(ShoppingCart s, String prType){
        for(Product p : s.getContent()){
            if(p.getType().equals(prType)){
                int amount = p.getStorageAmount();
                s.getContent().remove(p);
                return amount;
            }
        }
        return 0;
    }

}
