package org.example;

import org.example.entity.Department;
import org.example.entity.Faculty;
import org.example.entity.Student;
import org.example.entity.Uniobject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.sql.PreparedStatement;
import java.util.List;

public class UniqueNumberSearchApp extends JFrame {

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

    public static List<Uniobject> searchEntitiesInDatabase() throws SQLException {
        List<String> uniqueNumbers = new ArrayList<>();
        uniqueNumbers = getUniqueNumbers();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Uniobject> uniobjects = new ArrayList<>();
        List<String> tableNames = Arrays.asList("uniobject", "people", "documents", "materialmeans", "subdivisions", "employees", "students",
                "paperdocuments", "electronicdocuments", "movable", "immovable", "faculty", "department", "teachers", "technicalstaff", "textdocument", "multimediadocument");


        Map<String, String> data = new LinkedHashMap<>();

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345");

        for (String number : uniqueNumbers) {
            JPanel resultPanel = new JPanel();
            resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
            String name = "";

            for (String tableName : tableNames) {

                String sqlQuery = "SELECT * FROM " + tableName + " WHERE uniqueno =" + number + "";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();


                if (resultSet.next()) {
                    resultPanel.add(new JLabel("Table: " + tableName));
                    name = tableName;

                    for (int i = 1; i <= metaData.getColumnCount(); i++) {

                        String columnName = metaData.getColumnName(i);
                        String value = resultSet.getString(i);
                        data.put(columnName, value);
                        resultPanel.add(new JLabel(columnName + ": " + value));
                    }
                }


            }

            if (name.equals("faculty")) {
                Faculty faculty = new Faculty();
                faculty.setId(Integer.valueOf(data.get("uniqueno")));
                faculty.setObjname(data.get("objname"));
                faculty.setMajor(Integer.valueOf(data.get("major")));
                faculty.setCurricula(data.get("curricula"));
                faculty.setFaclocation(data.get("faclocation"));
                faculty.setChef(data.get("chef"));
                uniobjects.add(faculty);
            } else if (name.equals("students")) {
                Student student = new Student();
                student.setId(Integer.valueOf(data.get("uniqueno")));
                student.setObjname(data.get("objname"));
                student.setMajor(Integer.valueOf(data.get("major")));
                student.setAveragemark(Float.valueOf(data.get("averagemark")));
                student.setUnigroup(data.get("unigroup"));
                if (data.get("practicalexperience") == null) {
                    student.setPracticalexperience(null);
                } else {
                    student.setPracticalexperience(Integer.parseInt(data.get("practicalexperience")));
                }

                LocalDate time = LocalDate.parse(data.get("dateofbirth"), formatter);


                student.setDateofbirth(time.atStartOfDay());
                student.setNationality(data.get("nationality"));
                student.setSex(data.get("sex"));
                uniobjects.add(student);

            } else if (name.equals("department")) {
                Department department = new Department();
                department.setId(Integer.valueOf(data.get("uniqueno")));
                department.setObjname(data.get("objname"));
                department.setMajor(Integer.valueOf(data.get("major")));
                department.setChef(data.get("chef"));
                department.setBudget(Float.valueOf(data.get("budget")));
                department.setTeachingfocus(data.get("teachingfocus"));
                uniobjects.add(department);
            }

        }
        return uniobjects;
    }


    public static void main(String[] args) throws SQLException {

        List<Uniobject> uniobjects = searchEntitiesInDatabase();
        for (Uniobject uniobject : uniobjects) {
            System.out.println(uniobject.getId());
            System.out.println(uniobject.getObjname());
            System.out.println(uniobject.getMajor());
            System.out.println(uniobject.getClass());
            System.out.println("__________________________________");
        }
    }
}
