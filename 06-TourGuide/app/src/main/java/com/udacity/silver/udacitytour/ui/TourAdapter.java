package com.udacity.silver.udacitytour.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity.silver.udacitytour.R;


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
            case 2:
                fragment = new SquishablesFragment();
                break;
            case 3:
                fragment = new VehiclesFragment();
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

        switch (position) {
            case 0:
                title = context.getString(R.string.title_conference);
                break;
            case 1:
                title = context.getString(R.string.title_snacks);
                break;
            case 2:
                title = context.getString(R.string.title_squish);
                break;
            case 3:
                title = context.getString(R.string.title_vehicles);
                break;
            default:
                title = "";
                break;
        }


        return title;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
