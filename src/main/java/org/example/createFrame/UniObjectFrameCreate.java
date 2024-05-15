package org.example.createFrame;

import org.example.dao.UniobjectDao;
import org.example.config.JdbcService;
import org.example.config.impl.PostgresJdbcService;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UniObjectFrameCreate extends JFrame {

    protected Integer major;
    protected Integer classId;
    protected final JdbcService jdbcService = new PostgresJdbcService();

    protected JTextField idField;
    protected JPanel panel = new JPanel(new GridLayout(10, 2));
    public JButton saveButton = new JButton("Save");

    public UniObjectFrameCreate(Integer classId, Integer major ) {

        this.classId = classId;
        this.major = major;


        setTitle("Uniobject Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Make the frame unresizable



        initComponents();


    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        panel.add(new JLabel("ID:"));
        idField = new JTextField(String.valueOf(0));
        idField.setEditable(true); // Make it uneditable
        panel.add(idField);
        panel.setBackground(new Color(204,153,255));
        panel.add(new JLabel("Object Name:"));
        JTextField nameField = new JTextField("");
        nameField.setEditable(true); // Make it uneditable
        panel.add(nameField);

        panel.add(new JLabel("Major:"));
        JTextField majorField = new JTextField(String.valueOf(major));
        majorField.setEditable(true); // Make it uneditable
        panel.add(majorField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // For demonstration, just print the current values
                System.out.println("ID: " + idField.getText());
                System.out.println("Object Name: " + nameField.getText());
                System.out.println("Major: " + majorField.getText());


                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("INSERT INTO uniobject values (?, ?, ?, ?)")){
                    ps.setInt(1, UniobjectDao.getTheBiggestIdFromUniobj()+1);
                    ps.setString(2, nameField.getText());
                    ps.setInt(3, Integer.parseInt(majorField.getText()));
                    ps.setInt(4, classId);
                    ps.executeUpdate();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                dispose();
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.setBackground(new Color(224,224,224));
        JButton cancelButton = createButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });
        buttonPanel.add(cancelButton);

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createButton(String text){
        return new JButton(text);
    }
}
