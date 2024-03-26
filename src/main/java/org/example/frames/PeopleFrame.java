package org.example.frames;

import org.example.entity.People;

import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class PeopleFrame extends UniobjectFrame {

    private People people;

    public PeopleFrame(People people) {
        super(people);
        this.people = people;
        setTitle("People Details");


        initComponents();
    }

    private void initComponents() {
        super.panel.remove(0);
        super.panel.remove(0);
        super.panel.remove(2);
        super.panel.remove(2);

        super.panel.add(new JLabel("Date of Birth:"));
        super.panel.add(new JLabel(people.getDateofbirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        super.panel.add(new JLabel("Sex:"));
        super.panel.add(new JLabel(people.getSex()));

        super.panel.add(new JLabel("Nationality:"));
        super.panel.add(new JLabel(people.getNationality()));


    }



}
