package com.arobs.servlet;


import com.arobs.model.Item;
import com.arobs.model.User;
import com.arobs.service.StorageService;
import com.arobs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {


    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    UserService userService = new UserService();
    StorageService storageService = new StorageService();

    @Override
    public void init() throws ServletException {
        super.init();
        userService.addUsers();
        storageService.addOrders();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") ;
        String password = req.getParameter("password");
        if(userService.validateUser(username,password)){
            User u = userService.getUserByUserName(username);
            ArrayList<Item> items = storageService.getAvailableOrders();
            req.getSession().setAttribute("listOfProducts", items);
            req.getSession().setAttribute("currentSessionUser",u);
            String s = "Last operation recorded: 0";
            req.getSession().setAttribute("MessageOp",s);
            userService.addUser(u);
            logger.info("User: {} logged in",username);
            resp.sendRedirect("userLogged.jsp");
        }
        else{
            resp.sendRedirect("invalidLogin.jsp");
            logger.info("Invalid credentials, no user with these dates found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
