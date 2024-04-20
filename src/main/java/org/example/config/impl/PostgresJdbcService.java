package org.example.config.impl;

import org.example.config.JdbcService;
import org.example.util.ResourceUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class PostgresJdbcService implements JdbcService {
    private Connection connection;

    public PostgresJdbcService(){
        final Map<String, String> map = ResourceUtil.getResources(this.getClass().getClassLoader());
        try {
            final String driver = map.get("jdbc.driver");
            Class.forName(driver);
            this.connection = DriverManager.getConnection(
                    map.get("jdbc.url"),
                    map.get("jdbc.username"),
                    map.get("jdbc.password")
            );
        } catch (SQLException e) {
            System.out.println("SQLException = " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException = " + e.getMessage());
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
