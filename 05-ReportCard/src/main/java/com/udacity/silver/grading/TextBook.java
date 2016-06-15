package com.udacity.silver.grading;

/**
 * Created by silver on 6/15/16.
 */

public class TextBook {

    private final TextBookType type;

    public TextBook(TextBookType type) {
        this.type = type;
    }

    public TextBookType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TextBook{" +
                "type=" + type +
                '}';
    }
}
