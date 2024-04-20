package org.example.createFrame;

import org.example.UniqueNumberSearchApp;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacultyFrameCreate extends SubdivisionFrameCreate {
    private JTextField facultyLocation, curricula;

    public FacultyFrameCreate(Integer classId, Integer major) {
        super(classId, major);
        initComponents();
        setTitle("Faculty Details");


    }

    private void initComponents() {

        super.panel.add(new JLabel("Faculty location:"));
        facultyLocation = new JTextField("");
        facultyLocation.setEditable(true); // Make it editable
        super.panel.add(facultyLocation);

        super.panel.add(new JLabel("Curricula:"));
        curricula = new JTextField("");
        curricula.setEditable(true); // Make it editable
        super.panel.add(curricula);


        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("INSERT INTO faculty values (?,?,?)")){
                    ps.setInt(1, UniqueNumberSearchApp.getTheBiggestIdFromUniobj()+1);
                    ps.setString(2, facultyLocation.getText());
                    ps.setString(3, curricula.getText());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }


}