package com.arobs.servlet;


import com.arobs.model.Item;
import com.arobs.model.Product;
import com.arobs.model.User;
import com.arobs.service.ProductService;
import com.arobs.service.StorageService;
import com.arobs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);

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
        logger.info("Reached add order to cart, Post method");
        ProductService productService = new ProductService();
        StorageService storageService = new StorageService();
        UserService userService = new UserService();
        String type = (String) req.getParameter("ProductType");
        String amount = (String) req.getParameter("amountI");
        ArrayList<Item> items = (ArrayList<Item>) req.getSession().getAttribute("listOfProducts");
        Product p = productService.getProductByType(items,type);
        Item o = new Item(p,Integer.parseInt(amount),false);
        User u = (User) req.getSession().getAttribute("currentSessionUser");
        try {
            userService.addOrderToCart(u,o);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            items = storageService.updateGlobalItem("add", items,type,o.getStorageAmount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
