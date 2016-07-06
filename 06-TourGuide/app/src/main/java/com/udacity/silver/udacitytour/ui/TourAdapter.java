package com.udacity.silver.udacitytour.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class TourAdapter extends FragmentPagerAdapter {

    private Context context;

    public TourAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new ConferenceRoomsFragment();
                break;
            case 1:
                fragment = new SnacksFragment();
                break;
            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title;

        switch (position){
            case 0:
                title = "Conference Rooms";
                break;
            case 1:
                title = "Snacks";
                break;
            default:
                title = "";
                break;
        }


        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
