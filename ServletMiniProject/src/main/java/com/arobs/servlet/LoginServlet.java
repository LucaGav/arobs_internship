package com.arobs.servlet;


import com.arobs.model.Product;
import com.arobs.model.ShoppingCart;
import com.arobs.model.User;
import com.arobs.service.ProductService;
import com.arobs.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.ProtectionDomain;
import java.util.ArrayList;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        UserService.addUsers();
        ProductService.addProducts();
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") ;
        String password = req.getParameter("password");
        if(UserService.validateUser(username,password)){
            User u = UserService.getUserByUserName(username);
            ArrayList<Product> products = ProductService.getProducts();
            req.getSession().setAttribute("listOfProducts",products);
            req.getSession().setAttribute("currentSessionUser",u);
            UserService.addUser(u);
            resp.sendRedirect("userLogged.jsp");
        }
        else{
            resp.sendRedirect("invalidLogin.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
