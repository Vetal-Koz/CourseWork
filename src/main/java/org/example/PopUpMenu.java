package org.example;

import org.example.createFrame.ListRelatedClassesFrame;
import org.example.entity.Uniobject;
import org.example.util.UniobjectUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PopUpMenu extends JPopupMenu {
    public PopUpMenu(Uniobject uniobject){
        JMenuItem updateItem = new JMenuItem("Update");
        JMenuItem insertItem = new JMenuItem("Insert");
        JMenuItem deleteItem = new JMenuItem("Delete");

        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UniobjectUtil.generateFrameUpdateForUniObj(uniobject).setVisible(true);
            }
        });

        insertItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> classes =  UniqueNumberSearchApp.getRelatedClassesById(Long.valueOf(uniobject.getClassId()));
                System.out.println(classes);
                ListRelatedClassesFrame relatedClassesFrame = new ListRelatedClassesFrame(classes, uniobject.getId());
                relatedClassesFrame.setVisible(true);;
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UniqueNumberSearchApp.deleteObjectById(uniobject.getId());
                JOptionPane.showMessageDialog(null, "Delete operation performed");
            }
        });

        add(updateItem);
        add(insertItem);
        add(deleteItem);
    }
}
