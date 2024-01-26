package org.example;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class TreeWithPanelExample extends JFrame {
    public TreeWithPanelExample() {
        // Створення кореневого вузла з панеллю
        JPanel rootPanel = new JPanel();
        rootPanel.add(new JLabel("Root Panel"));
        CustomTreeNode rootNode = new CustomTreeNode("Root", rootPanel);

        // Додавання дочірніх вузлів з панелями
        JPanel panelNode1 = new JPanel();
        panelNode1.add(new JLabel("Panel for Node 1"));
        CustomTreeNode node1 = new CustomTreeNode("Node 1", panelNode1);

        JPanel panelNode2 = new JPanel();
        panelNode2.add(new JLabel("Panel for Node 2"));
        CustomTreeNode node2 = new CustomTreeNode("Node 2", panelNode2);

        rootNode.add(node1);
        rootNode.add(node2);

        // Створення JTree з кореневим вузлом
        JTree tree = new JTree(rootNode);

        // Налаштування відображення вузлів
        tree.setCellRenderer(new CustomTreeCellRenderer());

        // Додавання JTree до JScrollPane для прокрутки
        JScrollPane scrollPane = new JScrollPane(tree);

        // Додавання JScrollPane до вікна
        add(scrollPane);

        // Налаштування вікна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tree with Panel Example");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TreeWithPanelExample());
    }
}


