package org.example.updateFrame;

import org.example.config.JdbcService;
import org.example.entity.Subdivision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SubdivisionFrameUpdate extends UniObjectFrameUpdate {
    private Subdivision subdivision;
    private JTextField chefField;


    public SubdivisionFrameUpdate(Subdivision subdivision) {
        super(subdivision);
        this.subdivision = subdivision;
        setTitle("Subdivision Detail");

        initComponents();
    }

    private void initComponents() {
        super.panel.remove(0);
        super.panel.remove(0);
        super.panel.remove(2);
        super.panel.remove(2);

        super.panel.add(new JLabel("Chef:"));
        chefField = new JTextField(subdivision.getChef());
        chefField.setEditable(true); // Make it editable
        super.panel.add(chefField);

        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("UPDATE subdivisions SET chef = ? where uniqueno = ?")){
                    ps.setString(1, chefField.getText());
                    ps.setLong(2, Long.parseLong(idField.getText()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        // Ensure the "Save" button is at the bottom


    }
}
