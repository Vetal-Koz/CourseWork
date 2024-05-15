package org.example.frames;

import org.example.entity.Uniobject;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UniobjectFrame extends JFrame {

    private Uniobject uniobject;
    protected JPanel panel = new JPanel(new GridLayout(6, 2));


    public UniobjectFrame(Uniobject uniobject) {


        this.uniobject = uniobject;


        setTitle("Uniobject Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        applyUIStyles();

        setVisible(true);
    }

    private void initComponents() {

        panel.add(createLabel("ID:"));
        panel.add(createLabel(String.valueOf(uniobject.getId())));

        panel.add(generateLabel("Object name:"));
        panel.add(generateValueLabel(uniobject.getObjname()));

        panel.add(createLabel("Major:"));
        panel.add(createLabel(String.valueOf(uniobject.getMajor())));

        add(panel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        return label;
    }

    protected JLabel generateLabel(String text){
        return new JLabel(text);
    }
    protected JLabel generateValueLabel(String value){
        return new JLabel(value);
    }
    private void applyUIStyles() {
        panel.setBackground(new Color(153, 204, 255)); // Dark blue gradient);
        // Setting background color for the panel
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20), // Padding around the panel
                BorderFactory.createSoftBevelBorder( // Soft bevel border for shadow effect
                        javax.swing.border.BevelBorder.RAISED,
                        new Color(200, 200, 200), // Light shadow color
                        new Color(255, 255, 255)) // Highlight color
        ));
    }
}
