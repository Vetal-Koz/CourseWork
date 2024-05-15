package org.example.controller;

import org.example.dao.UniobjectDao;
import org.example.entity.Uniobject;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class NewApp extends JPanel {

    private DynamicTree treePanel;

    private List<Uniobject> uniobjectList;

    public NewApp(List<Uniobject> uniobjectList) throws SQLException {
        super(new BorderLayout());
        this.uniobjectList = uniobjectList;

        treePanel = new DynamicTree();


        treePanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    System.out.println("dsada");
                    TreePath path = treePanel.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        System.out.println(path.getLastPathComponent());

                    }
                }
            }
        });
        populateTree(treePanel);



        treePanel.setPreferredSize(new Dimension(1000, 800));
        add(treePanel, BorderLayout.CENTER);
//
//        JPanel panel = new JPanel(new GridLayout(0,3));
//
//        add(panel, BorderLayout.SOUTH);
    }

    public void populateTree(DynamicTree treePanel) throws SQLException {
        DefaultMutableTreeNode p1, p2;

        List<Uniobject> uniobjectList = UniobjectDao.searchInstanceWithMajorNull();
        for (Uniobject uniobject1: uniobjectList){
            System.out.println(uniobject1);
            treePanel.addObject(null, uniobject1);
        }

    }
    private static void createAndShowGUI() throws SQLException {
        Font defaultFont = new Font("Serif", Font.BOLD, 14);
        UIManager.put("TextField.font", defaultFont);
        List<Uniobject> uniobjectList = UniobjectDao.searchInstanceWithMajorNull();
        JFrame frame = new JFrame("DynamicTreeDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NewApp newContentPane = new NewApp(uniobjectList);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
