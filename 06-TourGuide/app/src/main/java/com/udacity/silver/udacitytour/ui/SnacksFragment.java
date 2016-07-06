package com.udacity.silver.udacitytour.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.silver.udacitytour.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SnacksFragment extends Fragment {


    public SnacksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_snacks, container, false);
    }

}
