package org.example.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.example.dao.UniobjectDao;
import org.example.entity.Uniobject;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class TestNewDynamicTree extends JPanel{
    private List<Uniobject> uniobjectList;

    private JButton addButton;

    public TestNewDynamicTree(List<Uniobject> uniobjectList) throws SQLException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.uniobjectList = uniobjectList;
        for (int i = 0; i < uniobjectList.size(); i++) {
            DynamicTreeNew treePanel = new DynamicTreeNew(uniobjectList.get(i));
            treePanel.setPreferredSize(new Dimension(300, 200 / (uniobjectList.size())));


            add(treePanel, BorderLayout.CENTER);
        }
        for (int i = 0; i < this.getComponents().length; i++) {
            Component panelTree = this.getComponents()[i];
            if (panelTree instanceof DynamicTreeNew){
                addLogicForTree((DynamicTreeNew) panelTree);
            }
        }

    }

    public void addTree() {
        int oldSize = uniobjectList.size();
        this.uniobjectList = UniobjectDao.searchInstanceWithMajorNull();
        int newSize = uniobjectList.size();
        if (!CollectionUtils.isEmpty(uniobjectList) && oldSize < newSize) {
            DynamicTreeNew treePanel = new DynamicTreeNew(uniobjectList.getLast());
            addLogicForTree(treePanel);
            treePanel.setPreferredSize(new Dimension(300, 200 / (uniobjectList.size())));
            add(treePanel);
        }

        revalidate();
        repaint();
    }

    public void addLogicForTree(DynamicTreeNew panelTree){
        ((DynamicTreeNew) panelTree).tree.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                    int x = e.getX();
                    int y = e.getY();
                    System.out.println(x);
                    System.out.println(y);
                    TreePath path = ((DynamicTreeNew) panelTree).tree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {

                    }
                    else {
                        PopUpMenu popUpMenu = new PopUpMenu();
                        popUpMenu.show(e.getComponent(), e.getX(), e.getY());
                        popUpMenu.relatedClassesFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                addTree();
                                // You can perform any actions you need here
                            }
                        });
                        System.out.println(((DynamicTreeNew) panelTree).treeModel.getRoot());
                        popUpMenu.deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                DefaultMutableTreeNode root = (DefaultMutableTreeNode) ((DynamicTreeNew) panelTree).treeModel.getRoot();

                                if (root.getUserObject() instanceof Uniobject) {
                                    UniobjectDao.deleteObjectById(((Uniobject) root.getUserObject()).getId());
                                    deleteTree(panelTree);
                                }

                            }

                        });
                    }

                }
            }
        });
    }
    public void deleteTree(int x, int y) {
        int oldSize = uniobjectList.size();
        this.uniobjectList = UniobjectDao.searchInstanceWithMajorNull();
        int newSize = uniobjectList.size();
        if (oldSize > newSize) {
            super.remove(super.getComponentAt(x,y));
        }

        revalidate();
        repaint();
    }

    public void deleteTree(Component component){
        int oldSize = uniobjectList.size();
        this.uniobjectList = UniobjectDao.searchInstanceWithMajorNull();
        int newSize = uniobjectList.size();
        if (oldSize > newSize) {
            super.remove(component);
        }

        revalidate();
        repaint();
    }
    public static void createAndShowGUI() {
        Font defaultFont = new Font("Serif", Font.BOLD, 14);
        UIManager.put("TextField.font", defaultFont);
        List<Uniobject> uniobjectList = UniobjectDao.searchInstanceWithMajorNull();
        JFrame frame = new JFrame("DynamicTreeDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestNewDynamicTree newContentPane = null;
        try {
            newContentPane = new TestNewDynamicTree(uniobjectList);
            newContentPane.setOpaque(true);
            newContentPane.setSize(1000, 1000);

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
