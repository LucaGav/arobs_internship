package com.arobs.service;

import com.arobs.model.Item;
import com.arobs.model.Product;

import java.util.ArrayList;

public class ProductService {

    public Product getProductByType(ArrayList<Item> items, String type){
        for(Item i : items){
            if(i.getProduct().getType().equals(type)){
                return i.getProduct();
            }
        }
        return null;
    }
}
