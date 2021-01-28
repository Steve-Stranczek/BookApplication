package com.company.BookApplication.driver;

import java.sql.*;

public class MySqlDriver {
    private static final String url = System.getenv("mySqlBookDB");
    private static final String user = "root";
    private static final String password = System.getenv("mySqlRootPassword");
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public ResultSet getResultSet(String query) throws SQLException {
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public void closeConnection() {
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
