package com.udacity.silver.grading;


import java.util.List;

public class Student {

    private List<ReportCard> reportCards;
    private List<Class> enrolledClasses;
    private List<TextBook> checkedOutTextBooks;

    public Student(List<ReportCard> reportCards, List<Class> enrolledClasses, List<TextBook> checkedOutTextBooks) {
        this.reportCards = reportCards;
        this.enrolledClasses = enrolledClasses;
        this.checkedOutTextBooks = checkedOutTextBooks;
    }

    public List<ReportCard> getReportCards() {
        return reportCards;
    }

    public void setReportCards(List<ReportCard> reportCards) {
        this.reportCards = reportCards;
    }

    public List<Class> getEnrolledClasses() {
        return enrolledClasses;
    }

    public void setEnrolledClasses(List<Class> enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }

    public List<TextBook> getCheckedOutTextBooks() {
        return checkedOutTextBooks;
    }

    public void setCheckedOutTextBooks(List<TextBook> checkedOutTextBooks) {
        this.checkedOutTextBooks = checkedOutTextBooks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "reportCards=" + reportCards +
                ", enrolledClasses=" + enrolledClasses +
                ", checkedOutTextBooks=" + checkedOutTextBooks +
                '}';
    }
}
