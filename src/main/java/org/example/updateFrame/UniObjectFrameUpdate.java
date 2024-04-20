package org.example.updateFrame;

import org.example.NewGUI;
import org.example.config.JdbcService;
import org.example.config.impl.PostgresJdbcService;
import org.example.entity.Uniobject;

import javax.swing.*;
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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Make the frame unresizable



        initComponents();


    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        panel.add(new JLabel("ID:"));
        idField = new JTextField(String.valueOf(uniobject.getId()));
        idField.setEditable(true); // Make it uneditable
        panel.add(idField);

        panel.add(new JLabel("Object Name:"));
        JTextField nameField = new JTextField(uniobject.getObjname());
        nameField.setEditable(true); // Make it uneditable
        panel.add(nameField);

        panel.add(new JLabel("Major:"));
        JTextField majorField = new JTextField(String.valueOf(uniobject.getMajor()));
        majorField.setEditable(true); // Make it uneditable
        panel.add(majorField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // For demonstration, just print the current values
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

                Window window = Window.getWindows()[0];
                if (window instanceof NewGUI) {
                    NewGUI frame = (NewGUI) window;
                    try {
                        frame.repaintTree();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                dispose();

            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
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
}
