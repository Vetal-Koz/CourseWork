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
        super.panel.add(generateLabel("Budget:"));
        super.panel.add(generateValueLabel(String.valueOf(department.getBudget())));

        super.panel.add(generateLabel("Teaching focus"));
        super.panel.add(generateValueLabel(department.getTeachingfocus()));
    }
}
