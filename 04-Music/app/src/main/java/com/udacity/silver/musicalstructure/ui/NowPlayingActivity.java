package com.udacity.silver.musicalstructure.ui;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.udacity.silver.musicalstructure.R;

public class NowPlayingActivity extends AppCompatActivity {


    private ImageView playPause;

    private AnimatedVectorDrawable playToPause;
    private AnimatedVectorDrawable pauseToPlay;

    private boolean play = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        playPause = (ImageView) findViewById(R.id.play_button);

        playToPause = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_play_to_pause);
        pauseToPlay = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_pause_to_play);

    }

    public void animate(View view) {


        AnimatedVectorDrawable drawable2 = play ? playToPause : pauseToPlay;
        playPause.setImageDrawable(drawable2);
        drawable2.start();
        play = !play;
    }
}
