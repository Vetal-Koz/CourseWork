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
        super.panel.add(generateLabel("Faculty Location:"));
        super.panel.add(generateValueLabel(faculty.getFaclocation()));

        super.panel.add(generateLabel("Curricula:"));
        super.panel.add(generateValueLabel(faculty.getCurricula()));


    }
}
