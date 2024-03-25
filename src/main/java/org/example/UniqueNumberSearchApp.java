package org.example;

import org.example.entity.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.sql.PreparedStatement;
import java.util.List;

public class UniqueNumberSearchApp extends JFrame {
    private static final List<String> TABLE_NAME = Arrays.asList("uniobject", "people", "documents", "materialmeans", "subdivisions", "employees", "students",
            "paperdocuments", "electronicdocuments", "movable", "immovable", "faculty", "department", "teachers", "technicalstaff", "textdocument", "multimediadocument");

    private static Connection connection = getConnection();

    public static List<Uniobject> searchInstanceWithMajorNull() throws SQLException {
        List<Uniobject> instancesWithMajorNull = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + "uniobject" + " WHERE major = 0 OR major IS NULL";



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

    private static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Uniobject> searchRelativeObjectsToObjectByMajor(Uniobject uniobject) throws SQLException {
        List<Uniobject> instancesWithMajorNull = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + "uniobject" + " WHERE major = " + uniobject.getId();

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345");

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();


        while (resultSet.next()) {
            Uniobject uniObj = new Uniobject();
            uniObj.setId(Integer.valueOf(resultSet.getString(1)));
            uniObj.setObjname(resultSet.getString(2));
            uniObj.setMajor(resultSet.getInt(3));
            uniObj.setClassId(resultSet.getInt(4));
            instancesWithMajorNull.add(uniObj);
        }
        return instancesWithMajorNull;
    }


    public static Map<String, Object> mapForFillUniobj(Uniobject uniobject) throws SQLException{
        List<String> tableNames = TABLE_NAME;
        Map<String, Object> data = new LinkedHashMap<>();

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/University", "postgres", "12345");

        for (String tableName : tableNames) {

            String sqlQuery = "SELECT * FROM " + tableName + " WHERE uniqueno =" + uniobject.getId() + "";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();


            if (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {

                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    if (value == null){
                        value = 0;
                    }
                    if (value instanceof Timestamp){
                        value = ((Timestamp) value).toLocalDateTime();
                    }
                    data.put(columnName, value);

                }
            }
        }
        return data;
    }

    public static String getNameOfClassUniObj(Uniobject uniobject){
       try (PreparedStatement ps = connection.prepareStatement("select classes.class_name from classes inner join public.uniobject u on classes.class_id = u.class_id where u.uniqueno = ?")){
           ps.setInt(1, uniobject.getId());
           ResultSet resultSet = ps.executeQuery();
           String name = "";
           if(resultSet.next()) {
               name = resultSet.getString(1);
           }
           return name;
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    public static String getFrameNameForUniObject(Uniobject uniobject){
        try (PreparedStatement ps = connection.prepareStatement("select classes.class_form from classes inner join public.uniobject u on classes.class_id = u.class_id where u.uniqueno = ?")){
            ps.setInt(1, uniobject.getId());
            ResultSet resultSet = ps.executeQuery();
            String form = "";
            if (resultSet.next()){
                form = resultSet.getString(1);
            }
            return form;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {

        Uniobject uniobject = new Uniobject(10, "Melnyk Ostap", 6, 3);

        getNameOfClassUniObj(uniobject);
        System.out.println(getFrameNameForUniObject(uniobject));
    }
}
