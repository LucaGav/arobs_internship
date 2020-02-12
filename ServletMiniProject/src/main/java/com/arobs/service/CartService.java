package com.arobs.service;

import com.arobs.model.Item;
import com.arobs.model.ShoppingCart;

import java.util.ArrayList;

public class CartService {

    public static void checkIfExists(ArrayList<Item> items, Item o){
        int ok = 0;
        for(Item oo: items){
            if(oo.getProduct().getType().equals(o.getProduct().getType())){
                oo.setStorageAmount(oo.getStorageAmount() + o.getStorageAmount());
                ok = 1;
            }
        }
        if(ok==0){
            items.add(o);
        }
    }

    public static int removeProduct(ShoppingCart s, String prType){
        for(Item o : s.getContent()){
            if(o.getProduct().getType().equals(prType)){
                int amount = o.getStorageAmount();
                s.getContent().remove(o);
                return amount;
            }
        }
        return 0;
    }

}
