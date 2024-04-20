package org.example;

import org.example.entity.*;
import org.example.frames.*;
import org.example.util.UniobjectUtil;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewGUI extends JFrame {

    private List<Uniobject> uniobjectList;

    private JScrollPane scrollPane;

    public NewGUI(List<Uniobject> uniobjectList) {
        this.uniobjectList = uniobjectList;
        initUI();
    }

    private void initUI() {
        setTitle("Uniobject List Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());


        for (Uniobject uniobject : uniobjectList) {

            CustomNode root = new CustomNode(uniobject);

            JTree tree1 = new JTree(root);
            tree1.addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {

                            TreePath path = tree1.getPathForLocation(e.getX(), e.getY());
                            if (path != null) {
                                if (path.getLastPathComponent() instanceof CustomNode) {
                                    CustomNode newTree = (CustomNode) path.getLastPathComponent();
                                    generateRelatedNode(newTree.getUniobject(), (CustomNode) path.getLastPathComponent());
                                }
                            }
                        } else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 3) {
                            TreePath path = tree1.getPathForLocation(e.getX(), e.getY());
                            if (path != null) {
                                if (path.getLastPathComponent() instanceof CustomNode) {
                                    CustomNode newTree = (CustomNode) path.getLastPathComponent();
                                    Uniobject fillObject = UniobjectUtil.generateClassByUniobj(newTree.getUniobject());
                                    UniobjectFrame uniobjectFrame = UniobjectUtil.generateFrameForUniObj(fillObject);
                                }
                            }
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            TreePath path = tree1.getPathForLocation(e.getX(), e.getY());
                            if (path != null) {
                                if (path.getLastPathComponent() instanceof CustomNode) {
                                    CustomNode newTree = (CustomNode) path.getLastPathComponent();
                                    Uniobject fillObject = UniobjectUtil.generateClassByUniobj(newTree.getUniobject());
                                    PopUpMenu popUpMenu = new PopUpMenu(fillObject);
                                    popUpMenu.show(e.getComponent(), e.getX(), e.getY());

                                }
                            }
                        }

                    }
                });
                this.scrollPane = new JScrollPane(tree1);

                scrollPane.setPreferredSize(new Dimension(1000, 800));

                add(scrollPane);


        }

        setSize(800, 600);

        setMinimumSize(new Dimension(1000, 1000));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void repaintTree() throws SQLException {
        this.dispose();
        List<Uniobject> uniobjectsList = UniqueNumberSearchApp.searchInstanceWithMajorNull();
        SwingUtilities.invokeLater(() -> new NewGUI(uniobjectsList));

    }

    private void generateRelatedNode(Uniobject uniobject, DefaultMutableTreeNode root) {
         try {
             List<Uniobject> relatedObjects = UniqueNumberSearchApp.searchRelativeObjectsToObjectByMajor(uniobject);
            for (Uniobject relatedObject : relatedObjects) {
                System.out.println(relatedObject);
                CustomNode node1 = new CustomNode(relatedObject);
                root.add(node1);
            }
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }

        revalidate();
    }

    public Map<Integer, List<Uniobject>> groupByMajor(List<Uniobject> uniobjects) {
        Map<Integer, List<Uniobject>> majorMap = new HashMap<>();
        for (int i = 0; i < uniobjects.size(); i++) {
            List<Uniobject> relatedObj = new ArrayList<>();
            for (int j = 0; j < uniobjects.size(); j++) {
                if (uniobjects.get(i).getId().equals(uniobjects.get(j).getMajor())) {
                    relatedObj.add(uniobjects.get(j));
                }
            }
            majorMap.put(uniobjects.get(i).getId(), relatedObj);
        }
        return majorMap;
    }

    public static void main(String[] args) throws SQLException {
        // Припустимо, у вас є список об'єктів Uniobject
        List<Uniobject> uniobjectList = UniqueNumberSearchApp.searchInstanceWithMajorNull();
        SwingUtilities.invokeLater(() -> new NewGUI(uniobjectList));

    }
}

