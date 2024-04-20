package org.example;

import org.example.config.JdbcService;
import org.example.config.impl.PostgresJdbcService;
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

    public static Integer getTheBiggestIdFromUniobj(){
        try (PreparedStatement ps = connection.prepareStatement("select max(uniqueno) from uniobject")){
            ResultSet resultSet = ps.executeQuery();
            Integer maxId = null;
            if (resultSet.next()){
                maxId = resultSet.getInt(1);
            }
            return maxId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFrameNameForUniObjectByClassId(int classId){
        try (PreparedStatement ps = connection.prepareStatement("select classes.class_form from classes where class_id = ?")){
            ps.setInt(1, classId);
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

    public static List<String> getRelatedClassesById(Long id) {
        JdbcService jdbcService = new PostgresJdbcService();
        List<String> classes = new ArrayList<>();

        try {


            PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("select c.class_name from class_relations inner join public.classes c on c.class_id = class_relations.child_class_id where parent_class_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classes.add(resultSet.getString(1));
            }
            return classes;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void deleteObjectById(Integer id){
        JdbcService jdbcService = new PostgresJdbcService();
        try {

            for (int i = 0; i < TABLE_NAME.size(); i++) {
                PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("delete from " + TABLE_NAME.get(i) + " where uniqueno = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static Integer getClassIdByName(String name) {
        JdbcService jdbcService = new PostgresJdbcService();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = jdbcService.getConnection().prepareStatement("select class_id from classes where class_name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            else {
                throw new RuntimeException("Such class do not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) throws SQLException {

    }
}
