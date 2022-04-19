package com.example.syedsubtainraza.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        textView = findViewById(R.id.splash_text);
        imageView = findViewById(R.id.splash_image);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        textView.startAnimation(myanim);
        imageView.startAnimation(myanim);

        Thread th = new Thread()
        {
            public void run() {
                try {
                    sleep(3000);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        th.start();
    }
}
