package com.example.vicky.justetit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vicky.justetit.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                Intent intent;
                if (isFirstStart) {
                    intent = new Intent(SplashScreenActivity.this, AppIntroActivity.class);
                    SharedPreferences.Editor editor = getPrefs.edit();
                    editor.putBoolean("firstStart", false);
                    editor.apply();
                } else
                    intent = new Intent(SplashScreenActivity.this, ContainerActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
