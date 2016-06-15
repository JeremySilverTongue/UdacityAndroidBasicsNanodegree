package com.udacity.silver.grading;


import java.util.Map;

public class ReportCard {

    private final Student student;
    private final Map<ClassCode, Grade> grades;

    public ReportCard(Student student, Map<ClassCode, Grade> grades) {
        this.student = student;
        this.grades = grades;
    }

    public Student getStudent() {
        return student;
    }

    public Map<ClassCode, Grade> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "student=" + student +
                ", grades=" + grades +
                '}';
    }

    public enum Grade {
        A("A"),
        B("B"),
        C("C"),
        D("D"),
        F("F"),
        W("W"),
        INC("INC");

        public final String letter;

        Grade(String letter) {
            this.letter = letter;
        }

        @Override
        public String toString() {
            return "Grade{" +
                    "letter='" + letter + '\'' +
                    '}';
        }
    }

}
