package com.arobs.service;

import com.arobs.model.Product;
import com.arobs.model.ShoppingCart;
import com.arobs.model.User;

import java.util.ArrayList;

public class UserService {
    private static ArrayList<User> users = new ArrayList<>();

    public static User getUser(int id){
        switch(id){
            case 1:
                return new User("Marius","Marius",null);
            case 2:
                return new User("Marco","Polo",null);
            case 3:
                return new User("Hayden","Hayden",null);
            case 4:
                return new User("Jacob","Jacob",null);
            default:
                return null;
        }
    }
    public static void addUser(User user){
        users.add(user);
    }

    public static void addUsers(){
        for(int i = 1 ; i <=4 ; i++){
            users.add(getUser(i));
        }
    }

    public static String getUsers() {
        String s = "Users registered:"+'\n';
        for (User u : users) {
            s += u.getUsername() + '\n';
        }
        return s;
    }

    public static boolean validateUser(String username, String password){
        for(User u : users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public static User getUserByUserName(String username){
        for(User u: users){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public static void addProductToCart(User u,Product p){
        ShoppingCart s = u.getCart();
        if(s == null){
            s = new ShoppingCart();
        }
        ArrayList<Product> prods;
        if(s.getContent()!=null) {
            prods = s.getContent();
            CartService.checkIfExists(prods,p);
            //prods.add(p);//vf
        }
        else {
            prods = new ArrayList<>();
            prods.add(p);
        }
        s.setContent(prods);
        u.setCart(s);
    }
}
