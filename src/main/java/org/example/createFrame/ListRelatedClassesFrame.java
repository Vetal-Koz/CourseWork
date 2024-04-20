package org.example.createFrame;

import org.example.UniqueNumberSearchApp;
import org.example.util.UniobjectUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ListRelatedClassesFrame extends JFrame {

    private Integer major;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    public ListRelatedClassesFrame(List<String> relatedClasses, Integer major) {
        setTitle("List Frame");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.major = major;
        // Creating a list model and populating it with elements
        listModel = new DefaultListModel<>();

        for (String element : relatedClasses) {
            listModel.addElement(element);
        }

        // Creating a JList with the populated list model
        list = new JList<>(listModel);


        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        // Creating buttons
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = list.getSelectedValue();
                Integer classId;

                if (selectedValue != null) {
                    classId = UniqueNumberSearchApp.getClassIdByName(selectedValue);
                    UniobjectUtil.generateFrameCreate(classId, major).setVisible(true);
                    JOptionPane.showMessageDialog(ListRelatedClassesFrame.this,
                            "Selected Element: " + selectedValue + " id: " + classId,
                            "Selected Element",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ListRelatedClassesFrame.this,
                            "Please select an element first.",
                            "No Element Selected",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });

        // Adding buttons to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClose);

        // Adding the button panel to the frame
        add(buttonPanel, BorderLayout.SOUTH);
    }


}