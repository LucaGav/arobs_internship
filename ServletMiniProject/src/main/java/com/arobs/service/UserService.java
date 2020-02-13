package com.arobs.service;

import com.arobs.dao.CartDaoJDBC;
import com.arobs.dao.ItemDaoJDBC;
import com.arobs.dao.UserDaoJDBC;
import com.arobs.model.Item;
import com.arobs.model.ShoppingCart;
import com.arobs.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user){
        users.add(user);
    }

    public static void addUsers(){
        List<User> usersAux = new ArrayList<>();
        try {
            usersAux = UserDaoJDBC.selectUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(User u: usersAux){
            users.add(u);
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

    public static void addOrderToCart(User u, Item o) throws SQLException {
        ShoppingCart s = u.getCart();
        if(s == null){
            s = CartDaoJDBC.createCart(u);
        }
        ArrayList<Item> items;
        if(s.getContent()!=null) {
            items = s.getContent();
            CartService.updateExistingOrder(items,o,s);
            //prods.add(p);//vf
        }
        else {
            items = new ArrayList<>();
            items.add(o);
            ItemDaoJDBC.addOrder(s,o);
        }
        s.setContent(items);
        u.setCart(s);
    }
}
