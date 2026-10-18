package studentgradetracker;

public class Student {

    private int rollNo;
    private String name;
    private double marks;

    public Student(int rollNo,String name, double marks){
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    public int getRollNo(){
        return rollNo;
    }

    public String getName(){
        return name;
    }

    public double getMarks(){
        return marks;
    }

    public String getGrade(){

        if(marks >= 90){
            return "A+";
        } else if(marks >= 80){
            return "A";
        } else if(marks >= 70){
            return "B";
        } else if(marks >= 60){
            return "C";
        } else if(marks >= 50){
            return "D";
        } else {
            return "F";
        }
    }
}
