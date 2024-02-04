package org.example.frames;

import org.example.entity.Uniobject;

import javax.swing.*;
import java.awt.*;


public class UniobjectFrame extends JFrame {

    private Uniobject uniobject;
    protected JPanel panel = new JPanel(new GridLayout(10, 2));

    public UniobjectFrame(Uniobject uniobject) {
        this.uniobject = uniobject;

        setTitle("Uniobject Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        panel.add(new JLabel("ID:"));
        panel.add(new JLabel(String.valueOf(uniobject.getId())));

        panel.add(new JLabel("Object Name:"));
        panel.add(new JLabel(uniobject.getObjname()));

        panel.add(new JLabel("Major:"));
        panel.add(new JLabel(String.valueOf(uniobject.getMajor())));


        add(panel);
    }

}
