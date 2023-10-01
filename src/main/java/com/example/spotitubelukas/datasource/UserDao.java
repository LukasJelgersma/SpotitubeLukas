package com.example.spotitubelukas.datasource;

import com.example.spotitubelukas.datasource.util.DatabaseProperties;
import com.example.spotitubelukas.domain.UserDTO;

import java.sql.*;
import java.util.logging.Logger;

public class UserDao {

    private Logger logger = Logger.getLogger(getClass().getName());

    DatabaseProperties databaseProperties = new DatabaseProperties();

    public void UserDao() {

    }

    public void setUserToken(UserDTO user){
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_TOKEN);

            updateStatement.setString(1, user.getUsertoken());
            updateStatement.setString(2, user.getUsername());

            // Execute the update statement
            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("User token updated successfully.");
            } else {
                logger.warning("User token update did not affect any rows.");
            }

            updateStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }
    }

    public UserDTO getUserCredentials(String username){
        UserDTO user = null;
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_USER_ALL);

            selectStatement.setString(1, username);

            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                user = new UserDTO(resultSet.getString("user"), resultSet.getString("password"),
                        resultSet.getString("name"), resultSet.getString("usertoken"));
            }

            selectStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);
        }

        return user;
    }

    public void addUser(UserDTO user) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement addStatement = connection.prepareStatement(SQL_INSERT);

            addStatement.setString(1, user.getUsername());
            addStatement.setString(2, user.getPassword());

            addStatement.execute();

            addStatement.close();
            connection.close();
        } catch (SQLException e) {
            logger.severe("Error communicating with database:" + e);

        }
    }

    private static final String SQL_INSERT = "INSERT INTO users (user, password) VALUES (?,?)";
    private static final String SQL_UPDATE_TOKEN = "UPDATE users SET usertoken = ? WHERE user = ?";
    private static final String SQL_SELECT_USER_ALL = "SELECT user, password, name, usertoken FROM users WHERE user = ?";

}
