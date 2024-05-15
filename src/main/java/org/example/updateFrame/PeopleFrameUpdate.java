package org.example.updateFrame;


import org.example.entity.People;
import org.example.style.CustomTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class PeopleFrameUpdate extends UniObjectFrameUpdate {


    private People people;
    private CustomTextField dateOfBirthField, sexField, nationalityField;

    public PeopleFrameUpdate(People people) {
        super(people);
        this.people = people;
        initComponents();
        setTitle("People Details");


    }

    private void initComponents() {
        super.panel.remove(0);
        super.panel.remove(0);
        super.panel.remove(2);
        super.panel.remove(2);

        super.panel.add(new JLabel("Date of Birth:"));
        dateOfBirthField = new CustomTextField(people.getDateofbirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dateOfBirthField.setEditable(true); // Make it editable
        super.panel.add(dateOfBirthField);

        super.panel.add(new JLabel("Sex:"));
        sexField = new CustomTextField(people.getSex());
        sexField.setEditable(true); // Make it editable
        super.panel.add(sexField);

        super.panel.add(new JLabel("Nationality:"));
        nationalityField = new CustomTextField(people.getNationality());
        nationalityField.setEditable(true); // Make it editable
        super.panel.add(nationalityField);

        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("UPDATE people SET dateofbirth = ?, sex = ?, nationality = ? where uniqueno = ?")){
                    ps.setTimestamp(1, Timestamp.valueOf(dateOfBirthField.getText() + " 00:00:00"));
                    ps.setString(2, sexField.getText());
                    ps.setString(3, nationalityField.getText());
                    ps.setLong(4, Long.parseLong(idField.getText()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });


    }



}
