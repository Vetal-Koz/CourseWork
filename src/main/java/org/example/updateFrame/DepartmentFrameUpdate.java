package org.example.updateFrame;

import org.example.entity.Department;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartmentFrameUpdate extends SubdivisionFrameUpdate{

    private Department department;
    private JTextField teachingFocus, budget;

    public DepartmentFrameUpdate(Department department) {
        super(department);
        this.department = department;
        initComponents();
        setTitle("Department Details");


    }

    private void initComponents() {

        super.panel.add(new JLabel("Teaching focus:"));
        teachingFocus = new JTextField(department.getTeachingfocus());
        teachingFocus.setEditable(true); // Make it editable
        super.panel.add(teachingFocus);

        super.panel.add(new JLabel("Budget:"));
        budget = new JTextField(department.getBudget().toString());
        budget.setEditable(true); // Make it editable
        super.panel.add(budget);

        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("UPDATE department SET teachingfocus = ?, budget = ?  where uniqueno = ?")){
                    ps.setString(1, teachingFocus.getText());
                    ps.setFloat(2, Float.parseFloat(budget.getText()));
                    ps.setLong(3, Long.parseLong(idField.getText()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }


}
