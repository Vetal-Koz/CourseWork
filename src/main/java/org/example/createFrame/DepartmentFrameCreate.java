package org.example.createFrame;

import org.example.dao.UniobjectDao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartmentFrameCreate extends SubdivisionFrameCreate{

    private JTextField teachingFocus, budget;

    public DepartmentFrameCreate(Integer classId, Integer major) {
        super(classId, major);
        initComponents();
        setTitle("Department Details");


    }

    private void initComponents() {

        super.panel.add(new JLabel("Teaching focus:"));
        teachingFocus = new JTextField("");
        teachingFocus.setEditable(true); // Make it editable
        super.panel.add(teachingFocus);

        super.panel.add(new JLabel("Budget:"));
        budget = new JTextField("");
        budget.setEditable(true); // Make it editable
        super.panel.add(budget);

        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("INSERT INTO department values (?,?,?)")){
                    ps.setInt(1, UniobjectDao.getTheBiggestIdFromUniobj()+1);
                    ps.setString(2, teachingFocus.getText());
                    ps.setFloat(3, Float.parseFloat(budget.getText()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }
}
