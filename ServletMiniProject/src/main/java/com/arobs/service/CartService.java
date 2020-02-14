package com.arobs.service;

import com.arobs.dao.CartDaoJDBC;
import com.arobs.dao.ItemDaoJDBC;
import com.arobs.model.Item;
import com.arobs.model.ShoppingCart;
import com.arobs.model.User;

import java.security.Provider;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartService {

    public void updateExistingOrder(ArrayList<Item> items, Item o, ShoppingCart s) throws SQLException {
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

    public int removeOrder(ShoppingCart s, String prType) throws SQLException {
        for(Item o : s.getContent()){
            if(o.getProduct().getType().equals(prType)){
                int amount = o.getStorageAmount();
                ItemDaoJDBC.removeOrderDao(o);
                s.getContent().remove(o);
                return amount;
            }
        }
        return 0;
    }

    public void deleteUserCart(User user, ArrayList<Item> items) throws SQLException {
        StorageService storageService = new StorageService();
        CartService cartService = new CartService();
        ShoppingCart s = user.getCart();
        for(Item i: s.getContent()){
            cartService.removeOrder(s,i.getProduct().getType());
            storageService.updateGlobalItem("delete",items,i.getProduct().getType(),i.getStorageAmount());
        }
        CartDaoJDBC.deleteCartDao(s);
    }

}
