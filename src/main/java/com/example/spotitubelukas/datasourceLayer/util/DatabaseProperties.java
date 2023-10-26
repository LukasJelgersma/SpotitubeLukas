package com.example.spotitubelukas.datasourceLayer.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
@ApplicationScoped
public class DatabaseProperties {

    private Logger logger = Logger.getLogger(getClass().getName());

    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't acces property file database.properties", e);
        }
    }

    public String connectionString(){
        return properties.getProperty("connectionString");
    }

}
