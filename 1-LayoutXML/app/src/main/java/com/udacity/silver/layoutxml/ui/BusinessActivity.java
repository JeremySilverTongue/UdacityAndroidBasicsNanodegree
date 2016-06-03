package com.udacity.silver.layoutxml.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.udacity.silver.layoutxml.R;

import java.util.Random;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessActivity extends AppCompatActivity {

    @BindView(R.id.walk_animation)
    ImageView walkAnimationView;

    @BindView(android.R.id.content)
    FrameLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);


        ButterKnife.bind(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {

            ((AnimationDrawable) walkAnimationView.getDrawable()).start();
        }
    }


    public void fabPressed(View view) {

        Random random = new Random();

        for (int i = 0; i < 100; i++) {


            final ImageView explosion = (ImageView) getLayoutInflater().inflate(R.layout.explosion, root, false);

            final AnimationDrawable drawable = (AnimationDrawable) explosion.getDrawable();

            explosion.setVisibility(View.INVISIBLE);


            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(200, 200);
            params.leftMargin = -100 + random.nextInt(root.getWidth());
            params.topMargin = -100 + random.nextInt(root.getHeight());
            root.addView(explosion, params);

            final long delay = random.nextInt(500);
            final long totalDuration = 300;

            Timer timer = new Timer();

            final Handler handler = new Handler(Looper.getMainLooper());

            final Runnable stop = new Runnable() {
                @Override
                public void run() {
                    root.removeView(explosion);
                }
            };

            Runnable start = new Runnable() {
                @Override
                public void run() {
                    explosion.setVisibility(View.VISIBLE);
                    drawable.start();
                    handler.postDelayed(stop, totalDuration);
                }
            };


            handler.postDelayed(start, delay);

        }


    }

    public void buyButtonPressed(View view) {

        Random random = new Random();

        for (int i = 0; i < 100; i++) {

            float height = random.nextFloat() * root.getHeight();
            int delay = random.nextInt(500);


            final View bullet = getLayoutInflater().inflate(R.layout.bullet, root, false);


            Animation animation = new TranslateAnimation(-100, root.getWidth(), height, height);

            animation.setDuration(200);
            animation.setStartOffset(delay);
            animation.setInterpolator(new LinearInterpolator());
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    root.removeView(bullet);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            bullet.setAnimation(animation);


            root.addView(bullet);
        }
    }


}
