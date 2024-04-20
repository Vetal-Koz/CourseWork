package org.example.updateFrame;

import org.example.entity.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StudentFrameUpdate extends PeopleFrameUpdate{

    private Student student;
    private JTextField unigroupField, averageMarkField, practicalExperienceField;

    public StudentFrameUpdate(Student student) {
        super(student);
        this.student = student;
        initComponents();
        setTitle("Student Details");


    }

    private void initComponents() {

        super.panel.add(new JLabel("Unigroup:"));
        unigroupField = new JTextField(student.getUnigroup());
        unigroupField.setEditable(true); // Make it editable
        super.panel.add(unigroupField);

        super.panel.add(new JLabel("Average mark:"));
        averageMarkField = new JTextField(student.getAveragemark().toString());
        averageMarkField.setEditable(true); // Make it editable
        super.panel.add(averageMarkField);

        super.panel.add(new JLabel("Practical Experience:"));
        practicalExperienceField = new JTextField(student.getPracticalexperience());
        practicalExperienceField.setEditable(true); // Make it editable
        super.panel.add(practicalExperienceField);

        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("UPDATE students SET unigroup = ?, averagemark = ?, practicalexperience = ? where uniqueno = ?")){
                    ps.setString(1, unigroupField.getText());
                    ps.setFloat(2, Float.parseFloat(averageMarkField.getText()));
                    ps.setInt(3, Integer.parseInt(practicalExperienceField.getText()));
                    ps.setLong(4, Long.parseLong(idField.getText()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

    }
}
