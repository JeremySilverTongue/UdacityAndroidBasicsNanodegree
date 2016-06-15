package com.udacity.silver.grading;

import java.util.List;

public class Class {

    private final String name;
    private final ClassCode code;
    private final TextBookType requiredBook;
    private Teacher teacher;
    private List<Student> enrolledStudents;

    public Class(String name, ClassCode code, TextBookType requiredBook, Teacher teacher, List<Student> enrolledStudents) {
        this.name = name;
        this.code = code;
        this.requiredBook = requiredBook;
        this.teacher = teacher;
        this.enrolledStudents = enrolledStudents;
    }

    public String getName() {
        return name;
    }

    public ClassCode getCode() {
        return code;
    }

    public TextBookType getRequiredBook() {
        return requiredBook;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    @Override
    public String toString() {
        return "Class{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", requiredBook=" + requiredBook +
                ", teacher=" + teacher +
                ", enrolledStudents=" + enrolledStudents +
                '}';
    }
}
