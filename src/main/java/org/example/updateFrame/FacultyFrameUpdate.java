package org.example.updateFrame;

import org.example.entity.Faculty;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacultyFrameUpdate extends SubdivisionFrameUpdate{
    private Faculty faculty;
    private JTextField facultyLocation, curricula;

    public FacultyFrameUpdate(Faculty faculty) {
        super(faculty);
        this.faculty = faculty;
        initComponents();
        setTitle("Faculty Details");


    }

    private void initComponents() {

        super.panel.add(new JLabel("Faculty location:"));
        facultyLocation = new JTextField(faculty.getFaclocation());
        facultyLocation.setEditable(true); // Make it editable
        super.panel.add(facultyLocation);

        super.panel.add(new JLabel("Curricula:"));
        curricula = new JTextField(faculty.getCurricula());
        curricula.setEditable(true); // Make it editable
        super.panel.add(curricula);


        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("UPDATE faculty SET faclocation = ?, curricula = ?  where uniqueno = ?")){
                    ps.setString(1, facultyLocation.getText());
                    ps.setString(2, curricula.getText());
                    ps.setLong(3, Long.parseLong(idField.getText()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }


}
