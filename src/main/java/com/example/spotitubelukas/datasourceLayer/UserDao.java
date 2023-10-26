package com.example.spotitubelukas.datasourceLayer;

import com.example.spotitubelukas.datasourceLayer.util.ConnectionManager;
import com.example.spotitubelukas.datasourceLayer.util.DatabaseProperties;
import com.example.spotitubelukas.resourceLayer.dto.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.logging.Logger;

@Default
@ApplicationScoped
public class UserDao {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private ConnectionManager connectionManager;

    public UserDao() {

    }

    public void setUserToken(UserDTO user) {
        Connection connection = connectionManager.ConnectionStart();

        try {
            PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE_TOKEN);

            updateStatement.setString(1, user.getUsertoken());
            updateStatement.setString(2, user.getUser());

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

    public UserDTO getUserCredentials(String username) {
        UserDTO user = null;
        Connection connection = connectionManager.ConnectionStart();
        try {
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

    public UserDTO getUserByToken(String token){
        Connection connection = connectionManager.ConnectionStart();
        UserDTO user = null;
        try {
            PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_USER_BY_TOKEN);

            selectStatement.setString(1, token);

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

    @Inject
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    private static final String SQL_UPDATE_TOKEN = "UPDATE users SET usertoken = ? WHERE user = ?";
    private static final String SQL_SELECT_USER_ALL = "SELECT user, password, name, usertoken FROM users WHERE user = ?";
    private static final String SQL_SELECT_USER_BY_TOKEN = "SELECT user, password, name, usertoken FROM users WHERE usertoken = ?";

}
