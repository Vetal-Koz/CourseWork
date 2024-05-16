package org.example.controller.transferHandler;

import org.example.dao.UniobjectDao;
import org.example.entity.Uniobject;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

public class TreeTransferHandler extends TransferHandler {
    DataFlavor nodesFlavor;
    DataFlavor[] flavors = new DataFlavor[1];
    DefaultMutableTreeNode[] nodesToRemove;


    public TreeTransferHandler() {
        try {
            String mimeType = DataFlavor.javaJVMLocalObjectMimeType +
                    ";class=\"" +
                    javax.swing.tree.DefaultMutableTreeNode[].class.getName() +
                    "\"";
            nodesFlavor = new DataFlavor(mimeType);
            flavors[0] = nodesFlavor;
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound: " + e.getMessage());
        }
    }

    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        support.setShowDropLocation(true);
        if (!support.isDataFlavorSupported(nodesFlavor)) {
            return false;
        }
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        JTree tree = (JTree) support.getComponent();
        TreePath dest = dl.getPath();
        DefaultMutableTreeNode target = (DefaultMutableTreeNode) dest.getLastPathComponent();
        TreePath path = tree.getSelectionPath();
        if (path == null) return false;
        DefaultMutableTreeNode draggedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (draggedNode == null) return false;
        if (draggedNode.getParent() == target) return false; // Disallow moving to same parent
        return true;
    }

    private DefaultMutableTreeNode copySingleNode(DefaultMutableTreeNode node) {
        return new DefaultMutableTreeNode(node.getUserObject());
    }
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        TreePath[] paths = tree.getSelectionPaths();
        if (paths != null) {
            List<DefaultMutableTreeNode> copies = new ArrayList<>();
            List<DefaultMutableTreeNode> toRemove = new ArrayList<>();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) paths[0].getLastPathComponent();
            copies.add(copySingleNodeWithoutChildren(node));
            toRemove.add(node);
            for (int i = 1; i < paths.length; i++) {
                DefaultMutableTreeNode next = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
                if (next.getLevel() < node.getLevel()) {
                    break;
                } else if (next.getLevel() > node.getLevel()) {
                    copies.add(copySingleNode(next));
                    toRemove.add(next);
                } else {
                    copies.add(copySingleNodeWithoutChildren(next));
                    toRemove.add(next);
                }
            }
            DefaultMutableTreeNode[] nodes = copies.toArray(new DefaultMutableTreeNode[0]);
            nodesToRemove = toRemove.toArray(new DefaultMutableTreeNode[0]);
            return new NodesTransferable(nodes);
        }
        return null;
    }

    private DefaultMutableTreeNode copySingleNodeWithoutChildren(DefaultMutableTreeNode node) {
        DefaultMutableTreeNode copy = new DefaultMutableTreeNode(node.getUserObject());
        // Do not add children
        return copy;
    }
    private DefaultMutableTreeNode copySubtree(DefaultMutableTreeNode node) {
        DefaultMutableTreeNode copy = new DefaultMutableTreeNode(node.getUserObject());
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            copy.add(copySubtree(child));
        }
        return copy;
    }

    protected void exportDone(JComponent source, Transferable data, int action) {
        if ((action & MOVE) == MOVE) {
            JTree tree = (JTree) source;
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            for (DefaultMutableTreeNode nodeToRemove : nodesToRemove) {
                model.removeNodeFromParent(nodeToRemove);
            }
        }
    }

    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }
        DefaultMutableTreeNode[] nodes = null;
        try {
            Transferable t = support.getTransferable();
            nodes = (DefaultMutableTreeNode[]) t.getTransferData(nodesFlavor);
        } catch (UnsupportedFlavorException ufe) {
            System.out.println("UnsupportedFlavor: " + ufe.getMessage());
        } catch (java.io.IOException ioe) {
            System.out.println("I/O error: " + ioe.getMessage());
        }
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        TreePath dest = dl.getPath();
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) dest.getLastPathComponent();
        JTree tree = (JTree) support.getComponent();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        for (DefaultMutableTreeNode node : nodes) {
            if (parent.equals(model.getRoot())) {
                model.insertNodeInto(node, parent, parent.getChildCount());
            } else if (parent.getChildCount() > 0) {
                model.insertNodeInto(node, parent, parent.getChildCount());
            }
            Object nodeObject = node.getUserObject();
            Object parentObject = parent.getUserObject();
            if (nodeObject instanceof Uniobject && parentObject instanceof Uniobject){

                UniobjectDao.updateUniobjectMajor((Uniobject) nodeObject, ((Uniobject) parentObject).getId());
            }
            else {
                UniobjectDao.updateUniobjectMajor((Uniobject) nodeObject, 0);
            }
        }
        return true;
    }

    public String toString() {
        return getClass().getName();
    }

    public class NodesTransferable implements Transferable {
        DefaultMutableTreeNode[] nodes;

        public NodesTransferable(DefaultMutableTreeNode[] nodes) {
            this.nodes = nodes;
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor))
                throw new UnsupportedFlavorException(flavor);
            return nodes;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return nodesFlavor.equals(flavor);
        }
    }
}