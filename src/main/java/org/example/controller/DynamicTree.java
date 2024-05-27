package org.example.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.example.dao.UniobjectDao;
import org.example.entity.Folder;
import org.example.entity.Uniobject;
import org.example.frames.UniobjectFrame;
import org.example.controller.transferHandler.TreeTransferHandler;
import org.example.util.StyleUtil;
import org.example.util.UniobjectUtil;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DynamicTree extends JPanel {
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;

    private final TreeTransferHandler transferHandler = new TreeTransferHandler();

    List<DefaultMutableTreeNode> usedNodes = new ArrayList<>();

    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    public DynamicTree() {
        super(new GridLayout(1,0));

        configureTree();
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    addLogicForMouseClickedCount2(e);
                }else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 3) {
                    addLogicForMouseClickedCount3(e);
                }else if (SwingUtilities.isRightMouseButton(e)) {
                    addLogicForMouseRightButton(e);
                }
            }
        });

        StyleUtil.setCustomStyles();


    }

    private void configureTree(){
        rootNode = new DefaultMutableTreeNode("");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(treeModel);

        tree.setRootVisible(false);
        tree.setEditable(false);

        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        addUniobjectsWithMajorNull();
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
        Font font = new Font("Arial", Font.PLAIN, 18);
        tree.setFont(font);

        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }

        tree.setDragEnabled(true);
        tree.setDropMode(DropMode.ON_OR_INSERT);
        tree.setTransferHandler(transferHandler);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
    }
    private void addLogicForMouseClickedCount2(MouseEvent e){
        Set<Uniobject> uniobjectSet = new HashSet<>();
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
        if (path != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (node != null) {
                if (!usedNodes.contains(node)) {
                    uniobjectSet = new HashSet<>(generateRelatedNode(node));
                    if (!CollectionUtils.isEmpty(uniobjectSet)){
                        usedNodes.add(node);
                    }
                }
            }
            for (Uniobject uniobject: uniobjectSet){
                addObject(node, uniobject);
            }
        }

    }

    private void addLogicForMouseClickedCount3(MouseEvent e){
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
        if (path != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (node != null) {
                if (node.getUserObject() instanceof Uniobject) {
                    Uniobject fillObject = UniobjectUtil.generateClassByUniobj((Uniobject) node.getUserObject());
                    if (!(fillObject instanceof Folder)) {
                        UniobjectFrame uniobjectFrame = UniobjectUtil.generateFrameForUniObj(fillObject);
                    }
                }
            }
        }
    }

    private void addLogicForMouseRightButton(MouseEvent e){
        TreePath path = tree.getPathForLocation(e.getX(), e.getY());
        if (path != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (node != null) {
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
                        if (countOldList < updateUniobjects.size()) {
                            addObject(node, updateUniobjects.getLast());
                        }
                    }
                });
            }
        }else {
            PopUpMenu popUpMenu = new PopUpMenu();
            popUpMenu.show(e.getComponent(), e.getX(), e.getY());
            int countOldList = UniobjectDao.searchInstanceWithMajorNull().size();
            popUpMenu.getRelatedClassesFrame().addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    List<Uniobject> updateUniobjects = UniobjectDao.searchInstanceWithMajorNull();
                    if (countOldList < updateUniobjects.size()){
                        addObject(null ,updateUniobjects.getLast());
                    }
                }
            });
        }
    }

    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    private void addUniobjectsWithMajorNull(){
        List<Uniobject> uniobjects = UniobjectDao.searchInstanceWithMajorNull();
        for (Uniobject uniobject: uniobjects){
            addObject(uniobject);
        }
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
