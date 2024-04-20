package org.example.config;

import java.sql.Connection;

public interface JdbcService {
    Connection getConnection();
}
