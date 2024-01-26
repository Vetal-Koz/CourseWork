package org.example;



import org.example.entity.Uniobject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame {

    private final List<Uniobject> uniobjectList;
    private  Map<Integer, List<Uniobject>> majorMap;

    public GUI(List<Uniobject> uniobjectList) {
        this.uniobjectList = uniobjectList;
        this.majorMap = groupByMajor(uniobjectList);
        initUI();
    }

    private void initUI() {
        setTitle("Uniobject List Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Створення інтерфейсу користувача для кожного об'єкта в списку
        for (Uniobject uniobject : uniobjectList) {
            if (uniobject.getMajor() == null || uniobject.getMajor() == 0) {
                CustomTreeNode root = new CustomTreeNode("Root", createPanel(uniobject));

//                List<Uniobject> relatedObjects = majorMap.get(uniobject.getId());
//                for (Uniobject relatedObject : relatedObjects) {
//                    JPanel node = createPanel(relatedObject);
//                    CustomTreeNode node1 = new CustomTreeNode("node", node);
//                    root.add(node1);
//                }
//                revalidate();
                generateRelatedNode(uniobject, root);


                JTree tree1 = new JTree(root);
                tree1.setCellRenderer(new CustomTreeCellRenderer());
                add(new JScrollPane(tree1));
            }
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generateRelatedNode(Uniobject uniobject,CustomTreeNode root){
        List<Uniobject> relatedObjects = majorMap.get(uniobject.getId());
        for (Uniobject relatedObject : relatedObjects) {
            JPanel node = createPanel(relatedObject);
            CustomTreeNode node1 = new CustomTreeNode("node", node);
            root.add(node1);
            generateRelatedNode(relatedObject, node1);
        }
        revalidate();
    }
    private JButton createButton(Uniobject uniobject) {
        JButton button = new JButton("Object " + uniobject.getId());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // При натисканні на кнопку виводимо інші об'єкти з відповідним major
                List<Uniobject> relatedObjects = majorMap.get(uniobject.getId());
                displayRelatedObjects(relatedObjects);
            }
        });
        return button;
    }

    private void displayRelatedObjects(List<Uniobject> relatedObjects) {
        // Вивід інших об'єктів
        for (Uniobject relatedObject : relatedObjects) {
            add(createPanel(relatedObject));
        }
        revalidate();
    }
    private JPanel createPanel(Uniobject uniobject) {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Object " + uniobject.getId()));

        panel.add(new JLabel("ID: "));
        panel.add(new JLabel(String.valueOf(uniobject.getId())));
        panel.add(new JLabel("Object Name: "));
        panel.add(new JLabel(uniobject.getObjname()));
        panel.add(new JLabel("Major: "));
        panel.add(new JLabel(String.valueOf(uniobject.getMajor())));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Код, який виконується при натисканні на панель
                System.out.println("Панель була натиснута!");
                List<Uniobject> relatedObjects = majorMap.get(uniobject.getId());
//                displayRelatedObjects(relatedObjects);
                relatedObjects.clear();
            }
        });

        return panel;
    }

    public Map<Integer, List<Uniobject>> groupByMajor(List<Uniobject> uniobjects) {
        Map<Integer, List<Uniobject>> majorMap = new HashMap<>();
        for(int i=0; i<uniobjects.size(); i++){
            List<Uniobject> relatedObj = new ArrayList<>();
            for (int j=0; j<uniobjects.size(); j++){
                if(uniobjects.get(i).getId().equals(uniobjects.get(j).getMajor())){
                    relatedObj.add(uniobjects.get(j));
                }
            }
            majorMap.put(uniobjects.get(i).getId(), relatedObj);
        }
        return majorMap;
    }

    public static void main(String[] args) throws SQLException {
        // Припустимо, у вас є список об'єктів Uniobject
        List<Uniobject> uniobjectList = UniqueNumberSearchApp.searchEntitiesInDatabase();
        SwingUtilities.invokeLater(() -> new GUI(uniobjectList));
        Map newMap = new GUI(uniobjectList).groupByMajor(uniobjectList);

        newMap.forEach( (k,v) -> System.out.println("Key: " + k + ": Value: " + v));

    }
}

