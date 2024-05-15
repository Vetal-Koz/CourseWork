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

        super.panel.add(generateLabel("Date of Birth:"));
        super.panel.add(generateValueLabel(people.getDateofbirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        super.panel.add(generateLabel("Sex:"));
        super.panel.add(generateValueLabel(people.getSex()));

        super.panel.add(generateLabel("Nationality:"));
        super.panel.add(generateValueLabel(people.getNationality()));


    }



}
