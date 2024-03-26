package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupMenuExample extends JFrame {
    private JPopupMenu popupMenu;

    public PopupMenuExample() {
        setTitle("Popup Menu Example");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        // Create a button
        JButton button = new JButton("Right Click Me!");
        button.setBounds(50, 50, 150, 30);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPopupMenu(button, e.getX(), e.getY());
                }
            }
        });
        add(button);

        // Create popup menu
        popupMenu = new JPopupMenu();
        JMenuItem updateItem = new JMenuItem("Update");
        JMenuItem insertItem = new JMenuItem("Insert");
        JMenuItem deleteItem = new JMenuItem("Delete");

        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Update operation performed");
            }
        });

        insertItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Insert operation performed");
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Delete operation performed");
            }
        });

        popupMenu.add(updateItem);
        popupMenu.add(insertItem);
        popupMenu.add(deleteItem);
    }

    private void showPopupMenu(Component component, int x, int y) {
        popupMenu.show(component, x, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PopupMenuExample().setVisible(true);
            }
        });
    }
}
