package com.udacity.silver.grading;

import java.util.List;

/**
 * Created by silver on 6/15/16.
 */

public class Teacher {

    private final String name;
    private List<Class> classes;

    public Teacher(String name, List<Class> classes) {
        this.name = name;
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "classes=" + classes +
                '}';
    }
}
