package com.example.firetopology;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    Animation topanim, bottomanim;
    ImageView logo;
    TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Context context;
        topanim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logo = findViewById(R.id.logo);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        logo.setAnimation(topanim);
        tv1.setAnimation(bottomanim);
        tv2.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(Splash.this, MainActivity.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  }, SPLASH_SCREEN

        );


    }
}