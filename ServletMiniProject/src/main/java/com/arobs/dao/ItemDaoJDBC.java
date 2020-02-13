package com.arobs.dao;

import com.arobs.dao.helper.DataSource;
import com.arobs.dao.helper.JDBCHelper;
import com.arobs.model.Item;
import com.arobs.model.ShoppingCart;

import java.sql.*;

public class ItemDaoJDBC {
    public static void addOrder(ShoppingCart cart, Item item) throws SQLException{
        int affectedRows = 0;
        PreparedStatement preparedStatement = null;
        try(Connection connection = DataSource.getConnection()){
            if(cart!=null) {
                preparedStatement = connection.prepareStatement("INSERT INTO item(productID,storageAmount,isGlobal,cartID)" +
                        "VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, item.getProduct().getProductID());
                preparedStatement.setInt(2, item.getStorageAmount());
                preparedStatement.setBoolean(3, false);
                preparedStatement.setInt(4,cart.getCartID());
                affectedRows = preparedStatement.executeUpdate();
            }
            else {
                preparedStatement = connection.prepareStatement("INSERT INTO item(productID,storageAmount,isGlobal)" +
                        "VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, item.getProduct().getProductID());
                preparedStatement.setInt(2, item.getStorageAmount());
                preparedStatement.setBoolean(3, true);
                affectedRows = preparedStatement.executeUpdate();
            }
                if (affectedRows == 0) {
                    throw new SQLException("Adding item failed, no rows affected.");
                }
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        item.setItemID(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating item failed, no ID obtained");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public static void updateExistingOrderDao(Item item) throws SQLException {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item set storageAmount = ? WHERE itemID = ?")){
            preparedStatement.setInt(1,item.getStorageAmount());
            preparedStatement.setInt(2,item.getItemID());
            preparedStatement.executeQuery();
        }
    }

    public static void updateGlobalOrderDao(Item item) throws SQLException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item set storageAmount = ? WHERE itemID = ? AND isGlobal = ?")) {
            preparedStatement.setInt(1, item.getStorageAmount());
            preparedStatement.setInt(2, item.getItemID());
            preparedStatement.setBoolean(3, true);
            preparedStatement.executeQuery();
        }
    }
}

