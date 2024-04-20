package org.example.createFrame;

import org.example.UniqueNumberSearchApp;
import org.example.entity.Subdivision;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubdivisionFrameCreate extends UniObjectFrameCreate{


    private JTextField chefField;


    public SubdivisionFrameCreate(Integer classId, Integer major) {
        super(classId, major);

        setTitle("Subdivision Detail");

        initComponents();
    }

    private void initComponents() {
        super.panel.remove(0);
        super.panel.remove(0);
        super.panel.remove(2);
        super.panel.remove(2);

        super.panel.add(new JLabel("Chef:"));
        chefField = new JTextField("");
        chefField.setEditable(true); // Make it editable
        super.panel.add(chefField);

        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("INSERT into subdivisions values (?, ?)")) {
                    ps.setInt(1, UniqueNumberSearchApp.getTheBiggestIdFromUniobj()+1);
                    ps.setString(2, chefField.getText());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }
}
