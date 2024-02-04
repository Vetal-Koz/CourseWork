package org.example.frames;

import org.example.entity.Department;

import javax.swing.*;

public class DepartmentFrame extends SubdivisionFrame {
    private Department department;

    public DepartmentFrame(Department department) {
        super(department);
        this.department = department;
        setTitle("Department Detail");
        initComponents();
    }

    private void initComponents() {
        super.panel.add(new JLabel("Budget:"));
        super.panel.add(new JLabel(String.valueOf(department.getBudget())));

        super.panel.add(new JLabel("Teaching Focus:"));
        super.panel.add(new JLabel(department.getTeachingfocus()));
    }
}
