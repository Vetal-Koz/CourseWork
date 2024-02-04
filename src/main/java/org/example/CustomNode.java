package org.example;

import org.example.entity.Uniobject;

import javax.swing.tree.DefaultMutableTreeNode;

public class CustomNode extends DefaultMutableTreeNode {
    private Uniobject uniobject;

    public CustomNode(Uniobject uniobject) {
        super(uniobject.getObjname());
        this.uniobject = uniobject;
    }

    public Uniobject getUniobject() {
        return uniobject;
    }

    public void setUniobject(Uniobject uniobject) {
        this.uniobject = uniobject;
    }
}
