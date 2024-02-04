package org.example.frames;


import org.example.entity.Subdivision;

import javax.swing.*;


public class SubdivisionFrame extends UniobjectFrame {
    private Subdivision subdivision;

    public SubdivisionFrame(Subdivision subdivision) {
        super(subdivision);
        this.subdivision = subdivision;
        setTitle("Subdivision Detail");


        initComponents();
    }

    private void initComponents() {


        super.panel.add(new JLabel("Chef:"));
        super.panel.add(new JLabel(subdivision.getChef()));


    }
}
