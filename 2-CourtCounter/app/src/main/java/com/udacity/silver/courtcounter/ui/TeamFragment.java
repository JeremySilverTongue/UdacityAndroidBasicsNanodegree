package com.udacity.silver.courtcounter.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.silver.courtcounter.R;

import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamFragment extends Fragment {


    @BindView(R.id.team_name)
    TextView teamName;

    @BindView(R.id.score)
    TextView scoreView;
    @BindView(R.id.plus2)
    Button plus2;
    @BindView(R.id.plus3)
    Button plus3;
    @BindView(R.id.plus1)
    Button plus1;
    @BindView(R.id.minus1)
    Button minus1;


    @BindString(R.string.score)
    String scoreFormat;
    private int score;


    public TeamFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_team, container, false);

        ButterKnife.bind(this, root);

        score = 0;
        updateScoreView();

        plus2.setOnClickListener(new ScoreIncrementer(2));
        plus3.setOnClickListener(new ScoreIncrementer(3));
        plus1.setOnClickListener(new ScoreIncrementer(1));
        minus1.setOnClickListener(new ScoreIncrementer(-1));


        return root;
    }

    void reset() {
        score = 0;
        updateScoreView();
    }

    private void updateScoreView() {
        scoreView.setText(String.format(Locale.getDefault(), scoreFormat, score));
    }

    private class ScoreIncrementer implements View.OnClickListener {

        private final int increment;

        public ScoreIncrementer(int increment) {
            this.increment = increment;
        }

        @Override
        public void onClick(View v) {
            score += increment;
            updateScoreView();
        }
    }

}
