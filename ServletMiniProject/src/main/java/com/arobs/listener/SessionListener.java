package com.arobs.listener;

import com.arobs.model.Item;
import com.arobs.model.User;
import com.arobs.service.CartService;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;
import java.util.ArrayList;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        CartService cartService = new CartService();
        HttpSession session = se.getSession();
        User u = (User) session.getAttribute("currentSessionUser");
        ArrayList<Item> items = (ArrayList<Item>) session.getAttribute("listOfProducts");
        try {
            cartService.deleteUserCart(u, items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //get data from user and remove cart
    }
}
