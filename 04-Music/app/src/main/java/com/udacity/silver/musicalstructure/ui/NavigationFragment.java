package com.udacity.silver.musicalstructure.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.udacity.silver.musicalstructure.R;
import com.udacity.silver.musicalstructure.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationFragment extends Fragment {

    @BindView(R.id.now_playing_button)
    Button nowPlaying;

    @BindView(R.id.browse_button)
    Button browse;

    @BindView(R.id.search_button)
    Button search;

    @BindView(R.id.radio_button)
    Button radio;

    @BindView(R.id.library_button)
    Button library;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        ButterKnife.bind(this, view);

        Utils.createClickListener(nowPlaying, getContext(), NowPlayingActivity.class);
        Utils.createClickListener(browse, getContext(), BrowseActivity.class);
        Utils.createClickListener(search, getContext(), SearchActivity.class);
        Utils.createClickListener(radio, getContext(), RadioActivity.class);
        Utils.createClickListener(library, getContext(), LibraryActivity.class);


        return view;
    }


}
