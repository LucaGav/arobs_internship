package com.arobs.dao;

import com.arobs.dao.helper.DataSource;
import com.arobs.dao.helper.JDBCHelper;
import com.arobs.model.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC {

    //DELETE * from shoppingcart WHERE shoppingcart.cartID = user.cartID AND user.username = ?

    public static List<User> selectUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT userID,username,password FROM user");
            ResultSet rs = preparedStatement.executeQuery()){
            while (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setUserID(rs.getInt("userID"));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
