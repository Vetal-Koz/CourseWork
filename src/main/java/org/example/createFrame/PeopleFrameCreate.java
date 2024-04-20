package org.example.createFrame;

import org.example.UniqueNumberSearchApp;
import org.example.entity.People;
import org.example.updateFrame.UniObjectFrameUpdate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class PeopleFrameCreate extends UniObjectFrameCreate {



    private JTextField dateOfBirthField, sexField, nationalityField;

    public PeopleFrameCreate(Integer classId, Integer major) {
        super(classId, major);
        initComponents();
        setTitle("People Details");


    }

    private void initComponents() {
        super.panel.remove(0);
        super.panel.remove(0);
        super.panel.remove(2);
        super.panel.remove(2);

        super.panel.add(new JLabel("Date of Birth:"));
        dateOfBirthField = new JTextField();
        dateOfBirthField.setEditable(true); // Make it editable
        super.panel.add(dateOfBirthField);

        super.panel.add(new JLabel("Sex:"));
        sexField = new JTextField();
        sexField.setEditable(true); // Make it editable
        super.panel.add(sexField);

        super.panel.add(new JLabel("Nationality:"));
        nationalityField = new JTextField();
        nationalityField.setEditable(true); // Make it editable
        super.panel.add(nationalityField);

        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("insert into people values (?,?,?,?)")){
                    ps.setInt(1, UniqueNumberSearchApp.getTheBiggestIdFromUniobj()+1);
                    ps.setTimestamp(2, Timestamp.valueOf(dateOfBirthField.getText() + " 00:00:00"));
                    ps.setString(3, sexField.getText());
                    ps.setString(4, nationalityField.getText());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });


    }



}
