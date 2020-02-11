package com.arobs.servlet;


import com.arobs.model.Order;
import com.arobs.model.Product;
import com.arobs.model.User;
import com.arobs.service.CartService;
import com.arobs.service.OrderService;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = (String) req.getParameter("ProductType");
        String amount = (String) req.getParameter("amountI");
        PrintWriter pr = resp.getWriter();
        pr.println(req.getParameter("amountI"));
        pr.println(req.getParameter("ProductType"));
        Product p = new Product(type);
        Order o = new Order(p,Integer.parseInt(amount));
        User u = (User) req.getSession().getAttribute("currentSessionUser");
        UserService.addOrderToCart(u,o);
        ArrayList<Order> orders = (ArrayList<Order>) req.getSession().getAttribute("listOfProducts");
        orders = OrderService.updateGlobalOrders("add",orders,p.getType(),o.getStorageAmount());
        req.getSession().setAttribute("listOfCartProducts",u.getCart().getContent());
        req.getSession().setAttribute("listOfProducts",orders);
        req.getSession().setAttribute("MessageOp","Last operation recorded: User " +
                u.getUsername() + " added product: " + p.getType() + " of amount: " + o.getStorageAmount());
        RequestDispatcher rd = req.getRequestDispatcher("userLogged.jsp");
        rd.forward(req,resp);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentSessionUser");
        String type = (String) req.getParameter("ProductType");
        int backAmount = CartService.removeProduct(u.getCart(),type);
        ArrayList<Order> orders = (ArrayList<Order>) req.getSession().getAttribute("listOfProducts");
        orders = OrderService.updateGlobalOrders("delete",orders,type,backAmount);
        req.getSession().setAttribute("listOfCartProducts",u.getCart().getContent());
        req.getSession().setAttribute("listOfProducts",orders);
        req.getSession().setAttribute("MessageOp","Last operation recorded: User " +
                u.getUsername() + " removed product: " + type);
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
        if(method.equals("GET")){
            String action = req.getParameter("action");
            if(action.equals("delete")){
                doDelete(req,resp);
            }
        }
    }

}
