package org.example.updateFrames;

import org.example.entity.Faculty;
import org.example.frames.SubdivisionFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FacultyFrameUpdate extends SubdivisionFrame {
    private Faculty faculty;
    private JTextField locationTextField; // JTextField for faculty location
    private JTextField curriculaTextField; // JTextField for curricula

    public FacultyFrameUpdate(Faculty faculty) {
        super(faculty);
        this.faculty = faculty;
        setTitle("Faculty Detail");
        initComponents();
    }

    private void initComponents() {
        super.panel.add(new JLabel("Faculty Location:"));
        locationTextField = new JTextField(faculty.getFaclocation(), 20); // Initialize with current value
        super.panel.add(locationTextField);

        super.panel.add(new JLabel("Curricula:"));
        curriculaTextField = new JTextField(faculty.getCurricula(), 20); // Initialize with current value
        super.panel.add(curriculaTextField);

        JButton updateButton = new JButton("Update"); // Create the update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the faculty object with new values
                String newLocation = locationTextField.getText();
                String newCurricula = curriculaTextField.getText();

                // Update faculty object
                faculty.setFaclocation(newLocation);
                faculty.setCurricula(newCurricula);

                System.out.println(faculty.getFaclocation());
                System.out.println(faculty.getCurricula());
                // Optionally, you can also perform other operations such as saving to database here

                // Notify user that update is successful
                JOptionPane.showMessageDialog(null, "Faculty details updated successfully");
            }
        });
        super.panel.add(updateButton); // Add the update button to the panel
    }

    public static void main(String[] args) {
        Faculty faculty1 = new Faculty(1,"phys",0,3,"chef", "ADSAD", "dsadasdsa");
        new FacultyFrameUpdate(faculty1);
    }
}
