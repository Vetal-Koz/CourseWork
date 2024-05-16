package org.example.dao;

import org.example.config.JdbcService;
import org.example.config.impl.PostgresJdbcService;
import org.example.entity.*;
import org.example.util.UniobjectUtil;

import java.sql.*;
import java.util.*;
import java.sql.PreparedStatement;
import java.util.List;

public class UniobjectDao {
    static JdbcService jdbcService = new PostgresJdbcService();
//    private static final List<String> TABLE_NAME = getAllTablesNameFromDB();


    public static List<Uniobject> searchInstanceWithMajorNull() {
        List<Uniobject> instancesWithMajorNull = new ArrayList<>();
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("SELECT * FROM uniobject WHERE major = 0 OR major IS NULL ORDER BY uniqueno")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Uniobject uniObj = new Uniobject();
                uniObj.setId(Integer.valueOf(resultSet.getString(1)));
                uniObj.setObjname(resultSet.getString(2));
                uniObj.setMajor(resultSet.getInt(3));
                uniObj.setClassId(resultSet.getInt(4));
                instancesWithMajorNull.add(uniObj);
            }
            return instancesWithMajorNull;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static List<Uniobject> searchRelativeObjectsToObjectByMajor(Uniobject uniobject){
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("SELECT * FROM uniobject WHERE major = ? order by uniqueno")){
            preparedStatement.setLong(1, uniobject.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Uniobject> instancesWithMajorNull = new ArrayList<>();
            while (resultSet.next()) {
                Uniobject uniObj = new Uniobject();
                uniObj.setId(Integer.valueOf(resultSet.getString(1)));
                uniObj.setObjname(resultSet.getString(2));
                uniObj.setMajor(resultSet.getInt(3));
                uniObj.setClassId(resultSet.getInt(4));
                instancesWithMajorNull.add(uniObj);
            }
            return instancesWithMajorNull;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getAllTablesNameFromDB(){
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("SELECT table_name FROM information_schema.tables" +
                " WHERE table_schema='public'" +
                "AND table_type='BASE TABLE'"
                +"ORDER BY table_name desc")
        ){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> tableNames = new ArrayList<>();
            while (resultSet.next()){
                tableNames.add(resultSet.getString(1));
            }
            tableNames.removeIf(t -> t.equals("class_relations"));
            tableNames.removeIf(t -> t.equals("classes"));
            return tableNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> mapForFillUniobj(Uniobject uniobject) {
        Map<String, Object> data = new LinkedHashMap<>();
        List<String> tablesName = getNamesOfTablesByUniobject(uniobject);
        for (String tableName: tablesName) {
            System.out.println(tableName);
            try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("SELECT * FROM " + tableName +" WHERE uniqueno = ?")) {
                preparedStatement.setInt(1, uniobject.getId());

                ResultSet resultSet = preparedStatement.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();

                if (resultSet.next()) {
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {

                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(i);
                        if (value == null) {
                            value = 0;
                        }
                        if (value instanceof Timestamp) {
                            value = ((Timestamp) value).toLocalDateTime();
                        }
                        data.put(columnName, value);

                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }

    private static List<String> getNamesOfTablesByUniobject(Uniobject uniobject){
        return UniobjectUtil.getAllTablesNameRelatedToUniobj(uniobject).reversed();
    }

    private static List<String> getNamesOfTablesByUniobjectId(Integer id){
        return UniobjectUtil.getAllTablesNameRelatedToUniobjById(id).reversed();
    }

    public static String getNameOfClassUniObj(Uniobject uniobject){
       try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("select classes.class_name from classes inner join public.uniobject u on classes.class_id = u.class_id where u.uniqueno = ?")){
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

    public static String getNameOfClassUniObjById(Integer uniobjectId){
        try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("select classes.class_name from classes inner join public.uniobject u on classes.class_id = u.class_id where u.uniqueno = ?")){
            ps.setInt(1, uniobjectId);
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
        try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("select classes.class_form from classes inner join public.uniobject u on classes.class_id = u.class_id where u.uniqueno = ?")){
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
        try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("select max(uniqueno) from uniobject")){
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
        try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("select classes.class_form from classes where class_id = ?")){
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

    public static List<String> getRelatedClassesById(Integer id) {
        JdbcService jdbcService = new PostgresJdbcService();
        List<String> classes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("select c.class_name from class_relations inner join public.classes c on c.class_id = class_relations.child_class_id where parent_class_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classes.add(resultSet.getString(1));
            }
            return classes;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllClasses(){
        JdbcService jdbcService = new PostgresJdbcService();
        List<String> classes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("select c.class_name from classes c");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classes.add(resultSet.getString(1));
            }
            return classes;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void updateUniobjectMajor(Uniobject uniobject, Integer majorId){
        JdbcService jdbcService = new PostgresJdbcService();
        try {
            PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("update uniobject set major = ? where uniqueno = ?");
            preparedStatement.setInt(1 ,majorId);
            preparedStatement.setInt(2, uniobject.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void deleteObjectById(Integer id){
        JdbcService jdbcService = new PostgresJdbcService();
        List<String> tablesName = getNamesOfTablesByUniobjectId(id);
        for (String tableName: tablesName) {
            try {
                PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("delete from " + tableName + " where uniqueno = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Integer getClassIdByName(String name) {
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("select class_id from classes where class_name = ?");){
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
}
