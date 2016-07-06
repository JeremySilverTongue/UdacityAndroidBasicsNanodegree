package com.udacity.silver.udacitytour.model;

import android.support.annotation.DrawableRes;

public class ConferenceRoom {


    public final String name;
    public final int chairs;

    @DrawableRes
    public final int imageId;

    public ConferenceRoom(String name, int chairs, int imageId) {
        this.name = name;
        this.chairs = chairs;
        this.imageId = imageId;
    }
}
