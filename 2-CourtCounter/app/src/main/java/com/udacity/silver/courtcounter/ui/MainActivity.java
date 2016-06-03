package com.udacity.silver.courtcounter.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.udacity.silver.courtcounter.R;

public class MainActivity extends AppCompatActivity {


    TeamFragment team1;
    TeamFragment team2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        team1 = (TeamFragment) getFragmentManager().findFragmentById(R.id.team1);
        team2 = (TeamFragment) getFragmentManager().findFragmentById(R.id.team2);

        team1.teamName.setText(R.string.hammer_head);
        team2.teamName.setText(R.string.the_storm);
    }

    public void reset(View view) {
        team1.reset();
        team2.reset();

    }
}
