package org.example;

import org.example.controller.TestNewDynamicTree;



public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TestNewDynamicTree.createAndShowGUI();
            }
        });
    }
}
