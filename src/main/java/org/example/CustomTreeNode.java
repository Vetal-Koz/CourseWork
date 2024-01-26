package org.example;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

class CustomTreeNode extends DefaultMutableTreeNode {
    private JPanel panel;

    public CustomTreeNode(String nodeName, JPanel panel) {
        super(nodeName);
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }
}