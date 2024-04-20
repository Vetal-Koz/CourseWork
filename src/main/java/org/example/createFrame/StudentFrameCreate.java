package org.example.createFrame;

import org.example.UniqueNumberSearchApp;
import org.example.entity.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentFrameCreate extends PeopleFrameCreate{

    private JTextField unigroupField, averageMarkField, practicalExperienceField;

    public StudentFrameCreate(Integer classId, Integer majorId) {
        super(classId, majorId);

        initComponents();
        setTitle("Student Details");


    }

    private void initComponents() {

        super.panel.add(new JLabel("Unigroup:"));
        unigroupField = new JTextField();
        unigroupField.setEditable(true); // Make it editable
        super.panel.add(unigroupField);

        super.panel.add(new JLabel("Average mark:"));
        averageMarkField = new JTextField();
        averageMarkField.setEditable(true); // Make it editable
        super.panel.add(averageMarkField);

        super.panel.add(new JLabel("Practical Experience:"));
        practicalExperienceField = new JTextField();
        practicalExperienceField.setEditable(true); // Make it editable
        super.panel.add(practicalExperienceField);

        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("insert into students values (?,?,?,?)")){
                    ps.setInt(1, UniqueNumberSearchApp.getTheBiggestIdFromUniobj()+1);
                    ps.setFloat(2, Float.parseFloat(averageMarkField.getText()));
                    ps.setString(3, unigroupField.getText());
                    ps.setInt(4, Integer.parseInt(practicalExperienceField.getText()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

    }
}
