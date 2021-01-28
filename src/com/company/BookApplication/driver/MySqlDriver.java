package com.company.BookApplication.driver;

import java.sql.*;

public class MySqlDriver {
    private static final String url = System.getenv("mySqlBookDB");
    private static final String user = "root";
    private static final String password = System.getenv("mySqlRootPassword");
    private final Connection connection;
    private final Statement statement;

    public MySqlDriver() throws SQLException {
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
    }

    public ResultSet getResultSet(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void closeConnection() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
