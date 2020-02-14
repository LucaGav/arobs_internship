package com.arobs.dao;

import com.arobs.dao.helper.DataSource;
import com.arobs.dao.helper.JDBCHelper;
import com.arobs.model.ShoppingCart;
import com.arobs.model.User;

import java.sql.*;

public class CartDaoJDBC {

    public static ShoppingCart createCart(User u) throws SQLException {
        ResultSet rs = null;
        ShoppingCart cart = new ShoppingCart();
        try (Connection con = DataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO shoppingcart(userID)" + "VALUES(?)", Statement.RETURN_GENERATED_KEYS)
             ){
            preparedStatement.setInt(1, u.getUserID());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating cart failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cart.setCartID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating cart failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        u.setCart(cart);
        return cart;
    }

    public static void deleteCartDao(ShoppingCart shoppingCart) throws SQLException {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM shoppingcart WHERE cartID = ?")){
            preparedStatement.setInt(1,shoppingCart.getCartID());
            preparedStatement.executeUpdate();
        }
    }
}
