package org.example.controller;

import lombok.Getter;
import org.example.createFrame.ListRelatedClassesFrame;
import org.example.dao.UniobjectDao;
import org.example.entity.Uniobject;
import org.example.util.UniobjectUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class PopUpMenu extends JPopupMenu {
    @Getter
    private List<String> relatedClasses = new ArrayList<>();
    JFrame updateFrame;

    @Getter
    JMenuItem deleteItem;

    private final Color MENU_BACKGROUND = Color.WHITE;
    private final Color MENU_FOREGROUND = Color.BLACK;

    @Getter
    ListRelatedClassesFrame relatedClassesFrame;
    public PopUpMenu(Uniobject uniobject, DefaultMutableTreeNode node){
        relatedClassesFrame = new ListRelatedClassesFrame(uniobject.getId(), node);
        JMenuItem updateItem = new JMenuItem("Update");
        JMenuItem insertItem = new JMenuItem("Insert");
        deleteItem = new JMenuItem("Delete");


        updateFrame = UniobjectUtil.generateFrameUpdateForUniObj(uniobject);
        relatedClasses = UniobjectDao.getRelatedClassesById((uniobject.getClassId()));

        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               updateFrame.setVisible(true);
            }
        });

        updateFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Uniobject fillObject = UniobjectUtil.generateClassByUniobj((Uniobject) node.getUserObject());
                node.setUserObject((Uniobject)fillObject);
            }
        });

        insertItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                relatedClassesFrame.setVisible(true);
            }
        });

        add(updateItem);
        add(insertItem);
        add(deleteItem);
    }

    public PopUpMenu(DefaultMutableTreeNode root){


        JMenuItem insertItem = new JMenuItem("Insert");
        relatedClasses = UniobjectDao.getAllClasses();
        relatedClassesFrame = new ListRelatedClassesFrame(0, root);
        insertItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                relatedClassesFrame.setVisible(true);
            }
        });
        add(insertItem);
    }

    public PopUpMenu(){
        JMenuItem insertItem = new JMenuItem("Insert");
        relatedClasses = UniobjectDao.getAllClasses();
        relatedClassesFrame = new ListRelatedClassesFrame(0);
        deleteItem = new JMenuItem("Delete");
        insertItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                relatedClassesFrame.setVisible(true);
            }
        });
        add(insertItem);
        add(deleteItem);
    }
}
