package com.arobs.dao.helper;

import java.sql.*;

public class JDBCHelper {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/shop";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "root";

    private static Connection connection;

    public static void registerDriver(){
        try
        {
            Class.forName(DRIVER);
        }
        catch ( ClassNotFoundException e )
        {
            System.out.println( "Driver class not found" );
        }
    }

    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(URL,USER,PASS);
        return connection;
    }

    public static void closeConnection(Connection conn) throws SQLException{
        if(conn!=null){
            conn.close();
        }
    }

    public static void closePrepaerdStatement( PreparedStatement stmt ) throws SQLException
    {
        if ( stmt != null )
        {
            stmt.close();
        }
    }

    public static void closeResultSet( ResultSet rs ) throws SQLException
    {
        if ( rs != null )
        {
            rs.close();
        }
    }
}
