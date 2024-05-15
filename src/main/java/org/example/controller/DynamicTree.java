package org.example.controller;

import org.example.dao.UniobjectDao;
import org.example.entity.Uniobject;
import org.example.frames.UniobjectFrame;
import org.example.util.StyleUtil;
import org.example.util.UniobjectUtil;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DynamicTree extends JPanel {
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public DynamicTree() {
        super(new GridLayout(1,0));

        rootNode = new DefaultMutableTreeNode("University");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.setEnabled(true);
        tree.setOpaque(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setEnabled(true);
        scrollPane.setOpaque(true);
        add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(3000, 3000));
        Font font = new Font("Arial", Font.PLAIN, 18);
        tree.setFont(font);
        List<DefaultMutableTreeNode> usedNodes = new ArrayList<>();

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (node != null) {
                            if (!usedNodes.contains(node)) {
                                List<Uniobject> uniobjectList = generateRelatedNode(node);
                                for (Uniobject uniobject : uniobjectList) {
                                    addObject(node, uniobject);
                                }
                                usedNodes.add(node);
                            }
                        } else {
                            System.out.println("Null node");
                        }
                    } else {
                        System.out.println("Null path");
                    }
                }else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 3) {
                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (node != null) {
                            if (node.getUserObject() instanceof Uniobject) {
                                Uniobject fillObject = UniobjectUtil.generateClassByUniobj((Uniobject) node.getUserObject());
                                UniobjectFrame uniobjectFrame = UniobjectUtil.generateFrameForUniObj(fillObject);
                            }
                        }
                    }
                }else if (SwingUtilities.isRightMouseButton(e)) {
                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (node != null) {
                            if (node.equals(rootNode)) {
                                PopUpMenu popUpMenu = new PopUpMenu(node);
                                popUpMenu.show(e.getComponent(), e.getX(), e.getY());
                                Uniobject uniobject = UniobjectDao.searchInstanceWithMajorNull().getLast();
                                popUpMenu.getRelatedClassesFrame().addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosed(WindowEvent e) {
                                        Uniobject newUniobject = null;
                                        newUniobject = UniobjectDao.searchInstanceWithMajorNull().getLast();
                                        if (!uniobject.getId().equals(newUniobject.getId())) {
                                            addObject(node, newUniobject);
                                        }
                                    }
                                });
                            } else {
                                Uniobject fillObject = UniobjectUtil.generateClassByUniobj((Uniobject) node.getUserObject());
                                PopUpMenu popUpMenu = new PopUpMenu(fillObject, node);
                                popUpMenu.show(e.getComponent(), e.getX(), e.getY());

                                popUpMenu.getDeleteItem().addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        removeCurrentNode();
                                        UniobjectDao.deleteObjectById(fillObject.getId());
                                        JOptionPane.showMessageDialog(null, "Delete operation performed");
                                    }
                                });

                                int countOldList = UniobjectDao.searchRelativeObjectsToObjectByMajor(fillObject).size();
                                popUpMenu.getRelatedClassesFrame().addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosed(WindowEvent e) {
                                        List<Uniobject> updateUniobjects = UniobjectDao.searchRelativeObjectsToObjectByMajor(fillObject);
                                        if (countOldList < updateUniobjects.size()){
                                            addObject(node, updateUniobjects.getLast());
                                        }
                                    }
                                });

                            }
                        }
                    }
                }
            }
        });


        setSize(800, 600);

        setMinimumSize(new Dimension(1000, 1000));


        setVisible(true);
        StyleUtil.setCustomStyles();


    }


    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                    (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }

        // Either there was no selection, or the root was selected.
        toolkit.beep();
    }

    /** Add child to the currently selected node. */
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                    (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    private List<Uniobject> generateRelatedNode(DefaultMutableTreeNode node) {

        Object obj = node.getUserObject();
        List<Uniobject> relatedObjects = new ArrayList<>();
        if (obj instanceof Uniobject) {
            relatedObjects = UniobjectDao.searchRelativeObjectsToObjectByMajor((Uniobject) obj);
        }
        return relatedObjects;


    }
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child,
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode =
                new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }

        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent,
                parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public TreePath getPathForLocation(int x, int y) {
        TreePath path = tree.getPathForLocation(x, y);
        return path;
    }

    class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

            /*
             * If the event lists children, then the changed
             * node is the child of the node we've already
             * gotten.  Otherwise, the changed node and the
             * specified node are the same.
             */

            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)(node.getChildAt(index));

            System.out.println("The user has finished editing the node.");
            System.out.println("New value: " + node.getUserObject());
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }
}
