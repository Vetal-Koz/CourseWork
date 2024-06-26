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

        super.panel.remove(0);
        super.panel.remove(0);
        super.panel.remove(2);
        super.panel.remove(2);
        super.panel.add(this.generateLabel("Chef:"));
        super.panel.add(this.generateValueLabel(subdivision.getChef()));


    }
}
