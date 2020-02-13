package com.arobs.service;

import com.arobs.dao.ItemDaoJDBC;
import com.arobs.model.Item;
import com.arobs.model.ShoppingCart;

import java.sql.SQLException;
import java.util.ArrayList;

public class CartService {

    public static void updateExistingOrder(ArrayList<Item> items, Item o, ShoppingCart s) throws SQLException {
        int ok = 0;
        for(Item oo: items){
            if(oo.getProduct().getType().equals(o.getProduct().getType())){
                oo.setStorageAmount(oo.getStorageAmount() + o.getStorageAmount());
                ItemDaoJDBC.updateExistingOrderDao(oo);
                ok = 1;
            }
        }
        if(ok==0){
            items.add(o);
            ItemDaoJDBC.addOrder(s,o);
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
