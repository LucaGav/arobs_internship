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
    private ArrayList<User> users = new ArrayList<>();

    public void addUser(User user){
        users.add(user);
    }

    public void addUsers(){
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

    public String getUsers() {
        String s = "Users registered:"+'\n';
        for (User u : this.users) {
            s += u.getUsername() + '\n';
        }
        return s;
    }

    public boolean validateUser(String username, String password){
        for(User u : this.users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    public User getUserByUserName(String username){
        for(User u: this.users){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public void addOrderToCart(User u, Item o) throws SQLException {
        CartService cartService = new CartService();
        ShoppingCart s = u.getCart();
        if(s == null){
            s = CartDaoJDBC.createCart(u);
        }
        ArrayList<Item> items;
        if(s.getContent()!=null) {
            items = s.getContent();
            cartService.updateExistingOrder(items,o,s);
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
