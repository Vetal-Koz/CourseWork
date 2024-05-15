package org.example.updateFrame;

import org.example.config.JdbcService;
import org.example.config.impl.PostgresJdbcService;
import org.example.entity.Uniobject;
import org.example.style.CustomTextField;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UniObjectFrameUpdate extends JFrame {


    protected final JdbcService jdbcService = new PostgresJdbcService();
    private Uniobject uniobject;
    protected JTextField idField;
    protected JPanel panel = new JPanel(new GridLayout(10, 2));
    protected JButton saveButton;

    public UniObjectFrameUpdate(Uniobject uniobject) {
        this.uniobject = uniobject;
        setTitle("Uniobject Details");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        panel.add(new JLabel("ID:"));

        idField = new JTextField(String.valueOf(uniobject.getId()));
        idField.setEditable(true);
        panel.add(idField);

        Border orangeBorder = BorderFactory.createLineBorder(Color.ORANGE, 2);

        panel.add(new JLabel("Object Name:"));
        CustomTextField nameField = new CustomTextField(uniobject.getObjname());
        nameField.setEditable(true);

        panel.add(nameField);


        panel.add(new JLabel("Major:"));
        JTextField majorField = new JTextField(String.valueOf(uniobject.getMajor()));
        majorField.setEditable(false);
        panel.add(majorField);

        panel.setBackground(new Color(204,153,255));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.saveButton = createButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ID: " + idField.getText());
                System.out.println("Object Name: " + nameField.getText());
                System.out.println("Major: " + majorField.getText());


                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("UPDATE uniobject SET objname = ? where uniqueno = ?")){
                    ps.setString(1, nameField.getText());
                    ps.setLong(2, Long.parseLong(idField.getText()));
                    ps.executeUpdate();


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                dispose();

            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });
        buttonPanel.add(cancelButton);
        buttonPanel.setBackground(new Color(224,224,224));
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createButton(String text){
        return new JButton(text);
    }

}
