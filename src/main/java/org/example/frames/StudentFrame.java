package org.example.frames;

import org.example.entity.Faculty;
import org.example.entity.Student;

import javax.swing.*;
import java.awt.*;

public class StudentFrame extends PeopleFrame {
    private Student student;

    public StudentFrame(Student student) {
        super(student);
        this.student = student;
        setTitle("Student Detail");


        initComponents();
    }

    private void initComponents() {

        // Add components to the panel
        super.panel.add(new JLabel("Average mark:"));
        super.panel.add(new JLabel(String.valueOf(student.getAveragemark())));

        super.panel.add(new JLabel("Group:"));
        super.panel.add(new JLabel(student.getUnigroup()));

        super.panel.add(new JLabel("Practical Experience:"));
        super.panel.add(new JLabel(String.valueOf(student.getPracticalexperience())));


    }
}
