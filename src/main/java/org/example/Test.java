package org.example;

import org.example.config.JdbcService;
import org.example.config.impl.PostgresJdbcService;
import org.example.createFrame.ListRelatedClassesFrame;
import org.example.createFrame.UniObjectFrameCreate;
import org.example.entity.*;
import org.example.frames.UniobjectFrame;
import org.example.updateFrame.*;
import org.example.util.UniobjectUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        List<Uniobject> list = UniqueNumberSearchApp.searchInstanceWithMajorNull();
//        Uniobject obj = UniobjectUtil.generateClassByUniobj(list.get(0));
//        System.out.println(obj);
//        UniobjectUtil.generateFrameUpdateForUniObj(obj).setVisible(true);
//
//
//
//
//        Faculty faculty = new Faculty(1,"Faculty", 3, 2, "chef", "TEa", "address");



    }

    public static List<String> getRelatedClassesById(Long id) throws SQLException {
        JdbcService jdbcService = new PostgresJdbcService();
        List<String> classes = new ArrayList<>();


        PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement("select c.class_name from class_relations inner join public.classes c on c.class_id = class_relations.child_class_id where parent_class_id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            classes.add(resultSet.getString(1));
        }
        return classes;
    }
}
