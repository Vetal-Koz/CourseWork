package org.example.createFrame;

import lombok.Getter;
import lombok.Setter;
import org.example.dao.UniobjectDao;
import org.example.entity.Uniobject;
import org.example.util.UniobjectUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class ListRelatedClassesFrame extends JFrame {

    private Integer major;
    private JList<String> list;

    public JButton btnSave;
    @Setter
    @Getter
    private List<Uniobject> relatedUniobjects = new ArrayList<>();
    private DefaultListModel<String> listModel;

    private List<String> relatedClasses;

    public UniObjectFrameCreate createFrame;

    public ListRelatedClassesFrame(Integer major, DefaultMutableTreeNode node) {
        setTitle("List Frame");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        this.major = major;
        if (major == 0){
            relatedClasses = UniobjectDao.getAllClasses();
        }

        if (node.getUserObject() instanceof Uniobject) {
            relatedClasses = UniobjectDao.getRelatedClassesById(((Uniobject) node.getUserObject()).getClassId());
        }
        listModel = new DefaultListModel<>();

        for (String element : relatedClasses) {
            listModel.addElement(element);
        }
        list = new JList<>(listModel);


        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        // Creating buttons
        JButton btnSave = createButton("Choose");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = list.getSelectedValue();
                Integer classId;

                if (selectedValue != null) {
                    classId = UniobjectDao.getClassIdByName(selectedValue);
                    createFrame = UniobjectUtil.generateFrameCreate(classId, major);
                    createFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            List<Uniobject> uniobjectList = generateRelatedNode(node);
                            for (Uniobject uniobject : uniobjectList) {

                                relatedUniobjects.add(uniobject);
                            }
                            // This method will be called when the frame is closed
                            System.out.println("Create frame closed");
                            dispose();
                            // You can perform any actions you need here
                        }
                    });
                    createFrame.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(ListRelatedClassesFrame.this,
                            "Please select an element first.",
                            "No Element Selected",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        JButton btnClose = createButton("Close");
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


    public ListRelatedClassesFrame(Integer major) {
        setTitle("List Frame");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        this.major = major;
        if (major == 0){
            relatedClasses = UniobjectDao.getAllClasses();
        }

        listModel = new DefaultListModel<>();

        for (String element : relatedClasses) {
            listModel.addElement(element);
        }
        list = new JList<>(listModel);


        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        // Creating buttons
        btnSave = createButton("Choose");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = list.getSelectedValue();
                Integer classId;

                if (selectedValue != null) {
                    classId = UniobjectDao.getClassIdByName(selectedValue);
                    createFrame = UniobjectUtil.generateFrameCreate(classId, major);
                    createFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            // This method will be called when the frame is closed
                            System.out.println("Create frame closed");
                            dispose();
                            // You can perform any actions you need here
                        }
                    });
                    createFrame.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(ListRelatedClassesFrame.this,
                            "Please select an element first.",
                            "No Element Selected",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        JButton btnClose = createButton("Close");
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

    private List<Uniobject> generateRelatedNode(DefaultMutableTreeNode node) {
        Object obj = node.getUserObject();
        List<Uniobject> relatedObjects = new ArrayList<>();
        if (obj instanceof Uniobject) {
            relatedObjects = UniobjectDao.searchRelativeObjectsToObjectByMajor((Uniobject) obj);
        }
        return relatedObjects;
    }

    private JButton createButton(String text){
        return new JButton(text);
    }

}