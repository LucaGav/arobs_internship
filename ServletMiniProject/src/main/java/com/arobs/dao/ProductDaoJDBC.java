package com.arobs.dao;

import com.arobs.dao.helper.DataSource;
import com.arobs.dao.helper.JDBCHelper;
import com.arobs.model.Product;
import com.arobs.model.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC {
    public static void addProduct(Product product) throws SQLException {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product(type)" + "VALUES(?)", Statement.RETURN_GENERATED_KEYS)
            ){
            preparedStatement.setString(1,product.getType());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean ifProductExists(String type) throws SQLException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement =  connection.prepareStatement("SELECT productID FROM product WHERE type = ?")
            ){
            preparedStatement.setString(1, type);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
