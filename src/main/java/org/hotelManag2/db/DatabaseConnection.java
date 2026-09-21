package org.hotelManag2.db;

import org.hotelManag2.config.DatabaseConfig;
import org.hotelManag2.exception.BusinessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        openConnection();
    }

    public static DatabaseConnection getInstance(){
        if(instance == null) {
            synchronized (DatabaseConnection.class){
                if(instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                openConnection();
            }
        } catch (SQLException e) {
            throw new BusinessException("Connexion à la base échouée : " + e.getMessage());
        }
        return connection;
    }

    private void openConnection() {
        try {
            DatabaseConfig config = DatabaseConfig.getInstance();
            this.connection = DriverManager.getConnection(
                    config.getUrl(), config.getUser(), config.getPassword()
            );
        } catch (SQLException e) {
            throw new BusinessException("Connexion à la base échouée : " + e.getMessage());
        }
    }
}