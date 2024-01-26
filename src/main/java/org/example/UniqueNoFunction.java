package org.example;

import javax.swing.*;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UniqueNoFunction {

    public static List<String> getUniqueNumbers() {
        List<String> uniqueNumbers = new ArrayList<>();

        // З'єднання з базою даних
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345")) {

            // SQL-запит для отримання унікальних значень з колонки uniqueno
            String sqlQuery = "SELECT DISTINCT uniqueno FROM uniobject";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                // Виконання SQL-запиту
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Додавання унікальних значень до масиву
                    while (resultSet.next()) {
                        String uniqueNo = resultSet.getString("uniqueno");
                        uniqueNumbers.add(uniqueNo);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uniqueNumbers;
    }

    public static List<String> getTableNames(Connection connection) {
        List<String> tableNames = new ArrayList<>();
        try {
            // Отримання метаданих бази даних
            DatabaseMetaData metaData = connection.getMetaData();

            // Отримання результату запиту до бази даних
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

            // Додавання імен таблиць до списку
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to get table names.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return tableNames;
    }


}
