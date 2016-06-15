package com.udacity.silver.grading;

public class TextBookType {

    private final String subject;
    private final String title;


    public TextBookType(String subject, String title) {
        this.subject = subject;
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "TextBookType{" +
                "subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
