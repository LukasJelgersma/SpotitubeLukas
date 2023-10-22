package com.example.spotitubelukas.datasource.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;

@Default
@ApplicationScoped
public class ConnectionManager {

    private Connection connection;
    private DatabaseProperties databaseProperties;

    public Connection ConnectionStart() {
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void ConnectionClose() {
        try {
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }
}
