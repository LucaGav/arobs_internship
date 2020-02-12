package com.arobs.dao;

import com.arobs.dao.helper.JDBCHelper;
import com.arobs.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC {


    public static List<User> selectUsers() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            JDBCHelper.registerDriver();
            connection = JDBCHelper.getConnection();
            if (connection == null) {
                System.out.println("Error getting the connection. Please check if the DB server is running");
            }
            preparedStatement = connection.prepareStatement("SELECT username,password FROM user");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCHelper.closeResultSet(rs);
                JDBCHelper.closePrepaerdStatement(preparedStatement);
                JDBCHelper.closeConnection(connection);
            } catch (SQLException e) {
                throw e;
            }
        }
        return users;
    }

}
