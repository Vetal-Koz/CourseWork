package org.example.frames;

import org.example.entity.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class StudentFrame extends PeopleFrame {
    private JFrame frame;
    private JPanel titlePanel;
    private JLabel title;

    private JPanel mainPanel;

    private JPanel infoPanel;

    private JPanel photoPanel;

    private JLabel surnameLabel;
    private String surname;
    private JLabel nameLabel;
    private String name;
    private String fatherName;
    private JLabel sexLabel;
    private String sex;
    private JLabel dateOfBirthLabel;
    private String dateOfBirth;
    private JLabel placeOfBirthLabel;
    private String placeOfBirth;
    private ImageIcon photoStudent;
    private JLabel photoLabel;
    private JLabel educationLabel;
    private String education;

    private JPanel tablePanel;
    private JTable table;

    private JPanel footPanel;

    private JLabel foreignLanguagesLabel;

    private String foreignLanguages;

    private JLabel scienceDegreeLabel;
    private String scienceDegree;

    private int widthOfFrame;
    private int heightOfFrame;

    private int busyHeightOfFrame;

    public StudentFrame(Student student){
        super(student);

        widthOfFrame = 790;
        heightOfFrame = 720;
        busyHeightOfFrame = 0;

        titlePanel = new JPanel();
//        titlePanel.setBackground(Color.red);
        titlePanel.setLayout(null);
        titlePanel.setBounds(0,0, widthOfFrame, 60);
        busyHeightOfFrame += 60;

        title = new JLabel();
        title.setText("Особовий Листок з Обліку Кадрів");
        title.setFont(title.getFont().deriveFont(100 ,25));
        title.setBounds(20,0, widthOfFrame, 40);
        titlePanel.add(title);


        mainPanel = new JPanel();
//        mainPanel.setBackground(Color.green);
        mainPanel.setLayout(null);
        mainPanel.setBounds(0,busyHeightOfFrame, widthOfFrame, 250);
        busyHeightOfFrame += 300;
        int busyWidthOfMainPanel = 0;

        infoPanel = new JPanel();
//        infoPanel.setBackground(Color.yellow);
        infoPanel.setBounds(0,0, widthOfFrame - 250, 200);
        infoPanel.setLayout(null);
        int busyWidthOfInfoPanel = 0;
        mainPanel.add(infoPanel);

        surnameLabel = new JLabel();
        surname = student.getObjname().split(" ")[0];
        fatherName = "Олегович";
        surnameLabel.setText("1.   Прізвище  " + surname);
        surnameLabel.setBounds(20, 0, infoPanel.getWidth(), 20);
        busyWidthOfInfoPanel += 150;
        infoPanel.add(surnameLabel);

        nameLabel = new JLabel();
        name = student.getObjname().split(" ")[1];
        nameLabel.setText("       ім'я  " + name +"  по батькові  " + fatherName);
        nameLabel.setBounds(20, surnameLabel.getHeight(), infoPanel.getWidth(), 20);
        busyWidthOfInfoPanel += 150;
        infoPanel.add(nameLabel);

        sexLabel = new JLabel();
        sex = student.getSex();
        sexLabel.setText("2.   Стать  " + sex);
        sexLabel.setBounds(20, surnameLabel.getHeight() + nameLabel.getHeight(), infoPanel.getWidth(), 20);
        busyWidthOfInfoPanel += 150;
        infoPanel.add(sexLabel);

        dateOfBirthLabel = new JLabel();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateOfBirth = student.getDateofbirth().format(formatter);


        dateOfBirthLabel.setText("3. Рік число і місяць народження     " + dateOfBirth);
        dateOfBirthLabel.setBounds(20, surnameLabel.getHeight() + nameLabel.getHeight() + sexLabel.getHeight(), infoPanel.getWidth(), 20);
        busyWidthOfInfoPanel += 150;
        infoPanel.add(dateOfBirthLabel);

        placeOfBirthLabel = new JLabel();
        placeOfBirth = "місто Львів, область Львівська";
        placeOfBirthLabel.setText("4.   Місце народження   " + placeOfBirth);
        placeOfBirthLabel.setBounds(20, surnameLabel.getHeight() + nameLabel.getHeight() + sexLabel.getHeight() + dateOfBirthLabel.getHeight(), infoPanel.getWidth(), 20);
        busyWidthOfInfoPanel += 150;
        infoPanel.add(placeOfBirthLabel);


//        surnameInfo = new JLabel();
//        surnameInfo.setText("Мельник");
//        surnameInfo.setBounds(20+busyWidthOfInfoPanel, 0, 100, 20);
//        busyWidthOfInfoPanel += 100;
//        infoPanel.add(surnameInfo);

        photoPanel = new JPanel();
//        photoPanel.setBackground(Color.blue);
        photoPanel.setBounds(infoPanel.getWidth(), 0, mainPanel.getWidth()-infoPanel.getWidth(), infoPanel.getHeight());
        photoPanel.setLayout(null);

        photoStudent = new ImageIcon("src/main/java/org/example/frames/student.png");
        photoStudent.getImage().getScaledInstance(photoPanel.getWidth(), photoPanel.getHeight(), Image.SCALE_SMOOTH);

        photoLabel = new JLabel();
        photoLabel.setBounds(0,0, photoPanel.getWidth(), photoPanel.getHeight());
        photoLabel.setIcon(new ImageIcon(photoStudent.getImage().getScaledInstance(photoPanel.getWidth(), photoPanel.getHeight(), Image.SCALE_SMOOTH)));
        photoLabel.setText("Ventalsdafaf");
        photoLabel.setVerticalAlignment(JLabel.CENTER);
        photoPanel.add(photoLabel);

        mainPanel.add(photoPanel);

        educationLabel = new JLabel();
        education = "Львівський національний університет імені Івана Франка";
        educationLabel.setText("5.   Освіта   " + education);
        educationLabel.setBounds(20, surnameLabel.getHeight() + infoPanel.getHeight(), mainPanel.getWidth(), 20);
        busyWidthOfInfoPanel += 150;
        mainPanel.add(educationLabel);


        tablePanel = new JPanel();
        String[] columNames = new String[]{
                "Найменування навчального закладу, його адреса",
                "Факультет або відділення",
                "Рік вступу",
                "Рік закінчення або відбуття",
                "Якщо не закінчив, то з якого курсу вибув",
                "Яку спеціальність одержав в результаті закінчення навчального закладу, вказати номер диплома або посвідчення"
        };

        String[][] data = {
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
        };
        table = new JTable(data, columNames);
        table.setBounds(0, 0, widthOfFrame, 200);
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 80));
        header.setDefaultRenderer(new MultiLineHeaderRenderer());
        table.setRowHeight(22);




//        tablePanel.setBackground(Color.orange);
        tablePanel.setLayout(null);
        tablePanel.setBounds(0,mainPanel.getHeight() + titlePanel.getHeight(), widthOfFrame, 200);




        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        scrollPane.setBounds(20, 0, widthOfFrame - 100, 193);


        footPanel = new JPanel();
//        footPanel.setBackground(Color.green);
        footPanel.setBounds(20,mainPanel.getHeight() + titlePanel.getHeight() + tablePanel.getHeight(), widthOfFrame - 100, 180);
        footPanel.setLayout(null);

        foreignLanguagesLabel = new JLabel();
        foreignLanguages = "English";
        foreignLanguagesLabel.setText("6.  Якими іноземними мовами володієте                   " + foreignLanguages);
        foreignLanguagesLabel.setBounds(0,0, footPanel.getWidth(), 40);

        busyWidthOfInfoPanel += 150;
        footPanel.add(foreignLanguagesLabel);

        scienceDegreeLabel = new JLabel();
        scienceDegree = "Бакалавр";
        scienceDegreeLabel.setText("7.  Науковий ступінь, вчене звання    " + scienceDegree);
        scienceDegreeLabel.setBounds(0,foreignLanguagesLabel.getHeight(), footPanel.getWidth(), 40);

        busyWidthOfInfoPanel += 150;
        footPanel.add(scienceDegreeLabel);



        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(widthOfFrame, heightOfFrame);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(titlePanel);
        frame.add(mainPanel);
        frame.add(tablePanel);
        frame.add(footPanel);

        super.setVisible(false);
        super.removeAll();
        super.repaint();
    }


    static class MultiLineHeaderRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (table != null) {
                JTableHeader header = table.getTableHeader();
                if (header != null) {
                    setForeground(header.getForeground());
                    setBackground(header.getBackground());
                    setFont(header.getFont());

                    setText(value != null ? value.toString() : "");
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    setHorizontalAlignment(JLabel.CENTER);
                    setVerticalAlignment(JLabel.TOP);

                    // Use HTML to allow multiline text
                    setText("<html><center>" + value + "</center></html>");
                }
            }
            return this;
        }
    }
    public static void main(String[] args) {
        Student student = new Student();
        student.setObjname("Melnyk Ostap");
        student.setSex("m");
        LocalDateTime dateTime = LocalDateTime.now();
        student.setDateofbirth(dateTime);
        SwingUtilities.invokeLater(() -> new StudentFrame(student));
    }
}
