package studentgradetracker;

import jdk.swing.interop.SwingInterOpUtils;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class StudentGradeTracker {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        displayMenu();

        while (true) {
            try{
                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();

                switch(choice){
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        displayStudents();
                        break;
                    case 3:
                        calculateAverage();
                        break;
                    case 4:
                        findHighest();
                        break;
                    case 5:
                        findLowest();
                        break;
                    case 6:
                        displaySummary();
                        break;
                    case 7:
                        System.out.println("Thank tou for using Student Grade Tracker..!");
                        return;

                    default:
                        System.out.println("Invalid choice! Please enter 1-7,");
                }
            } catch(InputMismatchException e){
                System.out.println("Invalid input! Please enter a number.");

                sc.nextLine();
            }
        }
    }

    static void displayMenu(){

        System.out.println("\n*******************************");
        System.out.println("      STUDENT GRADE TRACKER");
        System.out.println("*********************************");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Calculate Average");
        System.out.println("4. Find Highest Marks");
        System.out.println("5. Find Lowest Marks");
        System.out.println("6. Display Summary");
        System.out.println("7. Exit");
        System.out.println("*********************************");

    }

    static void addStudent(){
        System.out.print("Enter roll number: ");
        int rollNo=sc.nextInt();

        sc.nextLine();

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter marks: ");
        double marks = sc.nextInt();

        if(marks < 0 || marks > 100){
            System.out.println("Marks must be between 0 and 100.");
            return;
        }

        Student s = new Student(rollNo,name,marks);
        students.add(s);

        System.out.println("Student add successfully..!");
    }

    static void displayStudents(){

        if(students.isEmpty()){
            System.out.println("No student available.");
            return;
        }

        System.out.println("\n************* STUDENTS LIST *************");

        for(Student s : students){
            System.out.println(
                    "Roll No: " + s.getRollNo()
                            + " | Name: " + s.getName()
                            + " | Marks: " + s.getMarks()
                            + " | Grade: " + s.getGrade()
            );
        }
    }
    static void calculateAverage(){
        if(students.isEmpty()){
            System.out.println("No students available.");
            return;
        }
        double total =0;

        for(Student s : students ){
            total += s.getMarks();
        }

        double average = total/students.size();

        System.out.println("Average Marks:"+ " "+ average);
    }

    static void findHighest(){
        if(students.isEmpty()){
            System.out.println("No students available.");
            return;
        }
        Student highest = students.get(0);

        for(Student s : students){
            if(s.getMarks() > highest.getMarks()){
                highest = s;
            }
        }
        System.out.println("\nHighest Marks");
        System.out.println("Name: " + highest.getName());
        System.out.println("Marks: " + highest.getMarks());
    }
    static void findLowest() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        Student lowest = students.get(0);

        for (Student s : students) {

            if (s.getMarks() < lowest.getMarks()) {
                lowest = s;
            }
        }

        System.out.println("\nLowest Marks");
        System.out.println("Name: " + lowest.getName());
        System.out.println("Marks: " + lowest.getMarks());
    }
    static void displaySummary() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        double total = 0;
        Student highest = students.get(0);
        Student lowest = students.get(0);

        for (Student s : students) {

            total += s.getMarks();

            if (s.getMarks() > highest.getMarks()) {
                highest = s;
            }

            if (s.getMarks() < lowest.getMarks()) {
                lowest = s;
            }
        }

        double average = total / students.size();

        System.out.println("\n========== SUMMARY ==========");
        System.out.println("Total Students : " + students.size());
        System.out.println("Average Marks  : " + average);
        System.out.println("Highest Marks  : " + highest.getMarks());
        System.out.println("Highest Student: " + highest.getName());
        System.out.println("Lowest Marks   : " + lowest.getMarks());
        System.out.println("Lowest Student : " + lowest.getName());
        System.out.println("=============================");
    }
}
