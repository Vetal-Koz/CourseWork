package org.example.frames;

import org.example.entity.Faculty;

import javax.swing.*;

public class FacultyFrame extends SubdivisionFrame {
    private Faculty faculty;

    public FacultyFrame(Faculty faculty) {
        super(faculty);
        this.faculty = faculty;
        setTitle("Faculty Detail");
        initComponents();
    }

    private void initComponents() {
        super.panel.add(new JLabel("Faculty Location:"));
        super.panel.add(new JLabel(faculty.getFaclocation()));

        super.panel.add(new JLabel("Curricula:"));
        super.panel.add(new JLabel(faculty.getCurricula()));


    }
}
