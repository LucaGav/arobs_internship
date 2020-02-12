package com.arobs.servlet;


import com.arobs.model.Item;
import com.arobs.model.Product;
import com.arobs.model.User;
import com.arobs.service.StorageService;
import com.arobs.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = (String) req.getParameter("ProductType");
        String amount = (String) req.getParameter("amountI");
        PrintWriter pr = resp.getWriter();
        pr.println(req.getParameter("amountI"));
        pr.println(req.getParameter("ProductType"));
        Product p = new Product(type);
        Item o = new Item(p,Integer.parseInt(amount),false);
        User u = (User) req.getSession().getAttribute("currentSessionUser");
        UserService.addOrderToCart(u,o);
        ArrayList<Item> items = (ArrayList<Item>) req.getSession().getAttribute("listOfProducts");
        items = StorageService.updateGlobalOrders("add", items,p.getType(),o.getStorageAmount());
        req.getSession().setAttribute("listOfCartProducts",u.getCart().getContent());
        req.getSession().setAttribute("listOfProducts", items);
        req.getSession().setAttribute("MessageOp","Last operation recorded: User " +
                u.getUsername() + " added product: " + p.getType() + " of amount: " + o.getStorageAmount());
        RequestDispatcher rd = req.getRequestDispatcher("userLogged.jsp");
        rd.forward(req,resp);

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if(method.equals("POST")){
            PrintWriter pr = resp.getWriter();
            pr.println(req.getParameter("amountI"));
            doPost(req,resp);
        }
    }

}
