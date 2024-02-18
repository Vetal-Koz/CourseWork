package org.example.frames;

import javax.swing.*;
import java.awt.*;

public class StudentForm extends JFrame {

    private JLabel nameLabel, dobLabel, sexLabel, nationalityLabel, averageMarkLabel, groupLabel, experienceLabel;
    private JLabel nameData, dobData, sexData, nationalityData, averageMarkData, groupData, experienceData;
    private JButton submitButton;

    public StudentForm() {
        setTitle("Student Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Світло-сірий колір
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Student Information"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        nameLabel = new JLabel("Name:");
        nameData = new JLabel("John Doe"); // Приклад даних

        dobLabel = new JLabel("Date of Birth:");
        dobData = new JLabel("01/01/1990"); // Приклад даних

        sexLabel = new JLabel("Sex:");
        sexData = new JLabel("Male"); // Приклад даних

        nationalityLabel = new JLabel("Nationality:");
        nationalityData = new JLabel("American"); // Приклад даних

        averageMarkLabel = new JLabel("Average Mark:");
        averageMarkData = new JLabel("3.5"); // Приклад даних

        groupLabel = new JLabel("Group:");
        groupData = new JLabel("A1"); // Приклад даних

        experienceLabel = new JLabel("Practical Experience:");
        experienceData = new JLabel("Yes"); // Приклад даних

        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180)); // Синій колір
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> submitForm());

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        nameLabel.setFont(labelFont);
        dobLabel.setFont(labelFont);
        sexLabel.setFont(labelFont);
        nationalityLabel.setFont(labelFont);
        averageMarkLabel.setFont(labelFont);
        groupLabel.setFont(labelFont);
        experienceLabel.setFont(labelFont);

        Font dataFont = new Font("Arial", Font.PLAIN, 14);
        nameData.setFont(dataFont);
        dobData.setFont(dataFont);
        sexData.setFont(dataFont);
        nationalityData.setFont(dataFont);
        averageMarkData.setFont(dataFont);
        groupData.setFont(dataFont);
        experienceData.setFont(dataFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(dobLabel, gbc);
        gbc.gridx = 1;
        panel.add(dobData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(sexLabel, gbc);
        gbc.gridx = 1;
        panel.add(sexData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(nationalityLabel, gbc);
        gbc.gridx = 1;
        panel.add(nationalityData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(averageMarkLabel, gbc);
        gbc.gridx = 1;
        panel.add(averageMarkData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(groupLabel, gbc);
        gbc.gridx = 1;
        panel.add(groupData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(experienceLabel, gbc);
        gbc.gridx = 1;
        panel.add(experienceData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;


        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void submitForm() {
        // Ваш код обробки поданих даних тут
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentForm());
    }
}
