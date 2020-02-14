package com.arobs.service;

import com.arobs.dao.ItemDaoJDBC;
import com.arobs.dao.ProductDaoJDBC;
import com.arobs.model.Item;
import com.arobs.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class StorageService {
    private ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Item> getAvailableOrders() {
        return items;
    }

    public Item getOrder(int id){
        Product p = new Product();
        switch(id){
            case 1:
                p.setType("Mar");
                Item item = new Item(p,23,true);
                try {
                    if(!ProductDaoJDBC.ifProductExists(p.getType())) {
                        ProductDaoJDBC.addProduct(p);
                        ItemDaoJDBC.addOrder(null,item);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return item;
            case 2:
                p.setType("Par");
                Item item2 = new Item(p,13,true);
                try {
                    if(!ProductDaoJDBC.ifProductExists(p.getType())) {
                        ProductDaoJDBC.addProduct(p);
                        ItemDaoJDBC.addOrder(null,item2);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return item2;
            case 3:
                p.setType("Dar");
                Item item3 = new Item(p,15,true);
                try {
                    if(!ProductDaoJDBC.ifProductExists(p.getType())) {
                        ProductDaoJDBC.addProduct(p);
                        ItemDaoJDBC.addOrder(null,item3);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return item3;
            case 4:
                p.setType("Zar");
                Item item4 = new Item(p,16,true);
                try {
                    if(!ProductDaoJDBC.ifProductExists(p.getType())) {
                        ProductDaoJDBC.addProduct(p);
                        ItemDaoJDBC.addOrder(null,item4);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return item4;
            default:
                return null;
        }
    }

    public void addOrders(){
        for(int i = 1 ; i <=4 ; i++){
            this.items.add(getOrder(i));
        }
    }
    public void addOrder(Item item){
        this.items.add(item);
    }

    public ArrayList<Item> updateGlobalItem(String flag, ArrayList<Item> items, String productType, int amount) throws SQLException {
        if (flag.equals("add")) {
            for (Item o : items) {
                if (o.getProduct().getType().equals(productType)) {
                    o.setStorageAmount(o.getStorageAmount() - amount);
                    ItemDaoJDBC.updateGlobalItemDao(o);
                }
                break;
            }
            return items;
        } else {
            for (Item o : items) {
                if (o.getProduct().getType().equals(productType)) {
                    o.setStorageAmount(o.getStorageAmount() + amount);
                    ItemDaoJDBC.updateGlobalItemDao(o);
                }
            }
            return items;
        }
    }
}
