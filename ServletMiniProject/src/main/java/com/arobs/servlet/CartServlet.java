package com.arobs.servlet;


import com.arobs.model.Item;
import com.arobs.model.User;
import com.arobs.service.CartService;
import com.arobs.service.StorageService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CartServlet", urlPatterns = "/CartServlet")
public class CartServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentSessionUser");
        String type = (String) req.getParameter("ProductType");
        int backAmount = CartService.removeProduct(u.getCart(),type);
        ArrayList<Item> items = (ArrayList<Item>) req.getSession().getAttribute("listOfProducts");
        items = StorageService.updateGlobalOrders("delete", items,type,backAmount);
        req.getSession().setAttribute("listOfCartProducts",u.getCart().getContent());
        req.getSession().setAttribute("listOfProducts", items);
        req.getSession().setAttribute("MessageOp","Last operation recorded: User " +
                u.getUsername() + " removed product: " + type);
        RequestDispatcher rd = req.getRequestDispatcher("userLogged.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if(method.equals("GET")){
            String action = req.getParameter("action");
            if(action.equals("delete")){
                doDelete(req,resp);
            }
        }
    }
}
