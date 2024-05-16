package org.example.controller;

import org.example.dao.UniobjectDao;
import org.example.entity.Uniobject;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class TreeApp extends JPanel {

    private DynamicTree treePanel;

    private List<Uniobject> uniobjectList;

    public TreeApp(List<Uniobject> uniobjectList) throws SQLException {
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


        treePanel.setPreferredSize(new Dimension(1000, 800));
        add(treePanel, BorderLayout.CENTER);
    }

    public static void createAndShowGUI() {
        Font defaultFont = new Font("Serif", Font.BOLD, 14);
        UIManager.put("TextField.font", defaultFont);
        List<Uniobject> uniobjectList = UniobjectDao.searchInstanceWithMajorNull();
        JFrame frame = new JFrame("DynamicTreeDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreeApp newContentPane = null;
        try {
            newContentPane = new TreeApp(uniobjectList);
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);

            frame.setSize(1000, 1000);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
