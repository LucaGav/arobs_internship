package com.arobs.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(
            urlPatterns = "/testServlet",
            initParams = {
                    @WebInitParam(name = "param1", value = "salut"),
                    @WebInitParam(name = "param2", value = "salut2")
            }
    )
    public class ServletTest extends HttpServlet {

        @Override
        public void init() throws ServletException {
            super.init();
        }

        @Override
        public void destroy() {
            super.destroy();
        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param1 = getInitParameter("param1");
        String param2 = getInitParameter("param2");


        PrintWriter writer = resp.getWriter();

        writer.println("param1 = " + param1);
        writer.println("param2 = " + param2);
    }
}
