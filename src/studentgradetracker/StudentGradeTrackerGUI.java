package studentgradetracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradeTrackerGUI extends JFrame {
    private final ArrayList<Student> students =new ArrayList<>();

    private JTextField rollField;
    private JTextField nameField;
    private JTextField marksField;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentGradeTrackerGUI(){
        setTitle("Student Grade Tracker");
        setSize(850,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
    }

    private void createGUI(){
        setLayout(new BorderLayout(10,10));

        JLabel heading = new JLabel(
                "STUDENT GRADE TRACKER",
                SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial",Font.BOLD,24));

        add(heading, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3,2,10,10));

        rollField = new JTextField();
        nameField = new JTextField();
        marksField = new JTextField();

        inputPanel.add(new JLabel("Roll Number: "));
        inputPanel.add(rollField);

        inputPanel.add(new JLabel("Student Name: "));
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Marks(0-100): "));
        inputPanel.add(marksField);

        tableModel = new DefaultTableModel(
                new String[]{"Roll No","Name","Marks","Grade"},0
        ){
            @Override

            public boolean isCellEditable(int row,int coloum) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        JButton addButton = new JButton("Add Student");
        JButton averageButton = new JButton("Average");
        JButton highestButton = new JButton("Highest");
        JButton lowestButton = new JButton("Lowest");
        JButton summaryButton = new JButton("Summary");
        JButton clearButton = new JButton("Clear fields");

        JPanel buttonPanel = new JPanel(new GridLayout(2,3,8,8));

        buttonPanel.add(addButton);
        buttonPanel.add(averageButton);
        buttonPanel.add(highestButton);
        buttonPanel.add(lowestButton);
        buttonPanel.add(summaryButton);
        buttonPanel.add(clearButton);

        JPanel centerPanel =new JPanel(new BorderLayout(10,10));

        centerPanel.add(inputPanel,BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel,BorderLayout.SOUTH);

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(10,15,15,15)
        );

        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addStudent());
        averageButton.addActionListener(e -> calculateAverage());
        highestButton.addActionListener(e -> findHighest());
        lowestButton.addActionListener(e -> findLowest());
        summaryButton.addActionListener(e -> displaySummary());
        clearButton.addActionListener(e -> clearFields());
    }

    private void addStudent() {
        try {
            int rollNo = Integer.parseInt(rollField.getText().trim());

            String name = nameField.getText().trim();

            double marks = Double.parseDouble(
                    marksField.getText().trim()
            );

            if (rollNo <= 0) {
                JOptionPane.showMessageDialog(
                        this, "Roll number must be positive."
                );
                return;
            }
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Please enter the student's name.");
                return;
            }
            if (!Double.isFinite(marks) || marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.");
                return;
            }

            for (Student student : students) {
                if (student.getRollNo() == rollNo) {
                    JOptionPane.showMessageDialog(this, "This roll number already exists.");
                    return;
                }
            }
            Student student = new Student(rollNo, name, marks);
            students.add(student);

            tableModel.addRow(new Object[]{
                    student.getRollNo(),
                    student.getName(),
                    student.getMarks(),
                    student.getGrade(),
            });
            JOptionPane.showMessageDialog(
                    this, "Student added successfully!"
            );

            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid roll number and marks."
            );
        }
    }
    private void calculateAverage(){
        if(students.isEmpty()){
            showNoStudentsMessage();
            return;
        }
        double total = 0;
        for(Student student : students){
            total += student.getMarks();
        }

        double average = total/students.size();

        JOptionPane.showMessageDialog(
                this,
                String.format("Average Marks: %.2f", average)
        );
    }

    private void findHighest(){
        if(students.isEmpty()){
            showNoStudentsMessage();
            return;
        }

        Student highest = students.get(0);

        for(Student student : students){
            if(student.getMarks()>highest.getMarks()){
                highest = student;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Highest Scoring Student\n"+
                        "Name: " + highest.getName()+
                        "\nMarks: " + highest.getMarks()
        );
    }

    private void findLowest(){
        if(students.isEmpty()){
            showNoStudentsMessage();
            return;
        }
        Student lowest = students.get(0);

        for(Student student: students){
            if(student.getMarks()<lowest.getMarks()){
                lowest = student;
            }
        }
        JOptionPane.showMessageDialog(
                this,
                "" +
                        "Lowest Scoring student\n"+
                        "Name: " + lowest.getName()+
                        "\nMarks: " + lowest.getMarks()
        );
    }

    private void displaySummary() {
        if (students.isEmpty()) {
            showNoStudentsMessage();
            return;
        }
        double total = 0;
        Student highest = students.get(0);

        Student lowest = students.get(0);

        for (Student student : students) {
            total += student.getMarks();
            if (student.getMarks() > highest.getMarks()) {
                highest = student;
            }
            if (student.getMarks() < lowest.getMarks()) {
                lowest = student;
            }
        }
        double average = total / students.size();

        String summary = "Total Students: " +
                students.size() + "\nAverage Marks: " +
                String.format("%.2f", average) +
                "\nHighest: " + highest.getName() +
                " (" + highest.getMarks() + ")" +
                "\nLowest: " + lowest.getName() +
                " (" + lowest.getMarks() +
                ")";

        JOptionPane.showMessageDialog( this, summary, "Student Summary", JOptionPane.INFORMATION_MESSAGE );
    }
    private void clearFields() {
        rollField.setText("");
        nameField.setText("");
        marksField.setText("");
        rollField.requestFocus();
    }
    private void showNoStudentsMessage() {
        JOptionPane.showMessageDialog( this, "Please add a student first." );
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { StudentGradeTrackerGUI gui = new StudentGradeTrackerGUI();

            gui.setVisible(true);});
    }

}

