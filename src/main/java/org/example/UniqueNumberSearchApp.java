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
    private static final List<String> TABLE_NAME = Arrays.asList("uniobject", "people", "documents", "materialmeans", "subdivisions", "employees", "students",
            "paperdocuments", "electronicdocuments", "movable", "immovable", "faculty", "department", "teachers", "technicalstaff", "textdocument", "multimediadocument");
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

    public static List<Uniobject> searchInstanceWithMajorNull() throws SQLException {
        List<Uniobject> instancesWithMajorNull = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + "uniobject" + " WHERE major = 0 OR major IS NULL";

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();


        if (resultSet.next()) {
            Uniobject uniObj = new Uniobject();
            uniObj.setId(Integer.valueOf(resultSet.getString(1)));
            uniObj.setObjname(resultSet.getString(2));
            uniObj.setMajor(resultSet.getInt(3));
            uniObj.setClassId(resultSet.getInt(4));

            instancesWithMajorNull.add(uniObj);
        }
        return instancesWithMajorNull;
    }

    public static List<Uniobject> searchRelativeObjectsToObjectByMajor(Uniobject uniobject) throws SQLException {
        List<Uniobject> instancesWithMajorNull = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + "uniobject" + " WHERE major = " + uniobject.getId();

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();


        if (resultSet.next()) {
            Uniobject uniObj = new Uniobject();
            uniObj.setId(Integer.valueOf(resultSet.getString(1)));
            uniObj.setObjname(resultSet.getString(2));
            uniObj.setMajor(resultSet.getInt(3));
            uniObj.setClassId(resultSet.getInt(4));
            instancesWithMajorNull.add(uniObj);
        }
        return instancesWithMajorNull;
    }

    public static Uniobject createObjectClass(Uniobject uniobject) throws SQLException {
        String sqlQuery = "SELECT class_name FROM " + "classes" + " WHERE class_id = " + uniobject.getClassId();

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        Uniobject uniObjectWithClass = null;
        if (resultSet.next()) {
            String className = resultSet.getString(1);
            if (className.equals("Faculty")) {
                uniObjectWithClass = new Faculty();
            }
            if (className.equals("Department")) {
                uniObjectWithClass = new Department();
            }
            if (className.equals("Student")) {
                uniObjectWithClass = new Student();
            }
        }
        return uniObjectWithClass;
    }

    public static Uniobject fillUniObjWithData(Uniobject uniobject) throws SQLException {
        List<String> tableNames = TABLE_NAME;
        Map<String, String> data = new LinkedHashMap<>();

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String name = "";

        String class_name = "";

        String sqlQueryForClassName = "SELECT classes.class_name FROM classes WHERE class_id = " + uniobject.getClassId();
        PreparedStatement preparedStatementForClassName = connection.prepareStatement(sqlQueryForClassName);
        ResultSet resultSetForClassName = preparedStatementForClassName.executeQuery();
        if (resultSetForClassName.next()) {
            class_name = resultSetForClassName.getString(1);
        }


        for (String tableName : tableNames) {

            String sqlQuery = "SELECT * FROM " + tableName + " WHERE uniqueno =" + uniobject.getId() + "";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();


            if (resultSet.next()) {
                name = tableName;

                for (int i = 1; i <= metaData.getColumnCount(); i++) {

                    String columnName = metaData.getColumnName(i);
                    String value = resultSet.getString(i);
                    data.put(columnName, value);

                }
            }
        }
        switch (class_name) {
            case "Faculty":
                Faculty faculty = new Faculty();
                faculty.setId(Integer.valueOf(data.get("uniqueno")));
                faculty.setObjname(data.get("objname"));
                faculty.setMajor(Integer.valueOf(data.get("major")));
                faculty.setClassId(uniobject.getClassId());
                faculty.setCurricula(data.get("curricula"));
                faculty.setFaclocation(data.get("faclocation"));
                faculty.setChef(data.get("chef"));
                return faculty;

            case "Student":
                Student student = new Student();
                student.setId(Integer.valueOf(data.get("uniqueno")));
                student.setObjname(data.get("objname"));
                student.setMajor(Integer.valueOf(data.get("major")));
                student.setClassId(uniobject.getClassId());
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
                return student;


            case "Department":
                Department department = new Department();
                department.setId(Integer.valueOf(data.get("uniqueno")));
                department.setObjname(data.get("objname"));
                department.setMajor(Integer.valueOf(data.get("major")));
                department.setClassId(uniobject.getClassId());
                department.setChef(data.get("chef"));
                department.setBudget(Float.valueOf(data.get("budget")));
                department.setTeachingfocus(data.get("teachingfocus"));
                return department;


            default:
                return null;
            // Handle the default case if needed

        }
    }

    public static List<Uniobject> searchEntitiesInDatabase() throws SQLException {
        List<String> uniqueNumbers = new ArrayList<>();
        uniqueNumbers = getUniqueNumbers();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Uniobject> uniobjects = new ArrayList<>();
        List<String> tableNames = TABLE_NAME;


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

        List<Uniobject> list = searchInstanceWithMajorNull();
        list.stream().forEach(u -> {
            System.out.println(u.toString());
            try {
                List<Uniobject> relativeObj = searchRelativeObjectsToObjectByMajor(u);
                relativeObj.stream().forEach(o -> {
                    System.out.println(o.toString());
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("-------------------------------------------");

        Uniobject uniobject = fillUniObjWithData(list.getFirst());
        System.out.println("__________________________________");

    }
}
