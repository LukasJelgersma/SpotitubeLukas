package com.example.spotitubelukas.datasource.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {

    private Logger logger = Logger.getLogger(getClass().getName());

    Properties properties = new Properties();

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
