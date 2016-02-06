package com.example.vicky.justetit.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.vicky.justetit.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by vicky on 2/4/16.
 */
public class AppIntroActivity extends AppIntro {
    @Override
    public void init(Bundle savedInstanceState) {
        int greenColorValue = Color.parseColor("#2CD358");
        int blueColorValue = Color.parseColor("#22DCFF");
        int redColorValue = Color.parseColor("#F44336");
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.intro_one_title),
                getString(R.string.intro_one_description),
                R.drawable.intro_two,
                redColorValue
        ));
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.intro_two_title),
                getString(R.string.intro_two_description),
                R.drawable.intro_two,
                redColorValue
        ));
    }

    @Override
    public void onSkipPressed() {
        startActivity(new Intent(this, ContainerActivity.class));
        finish();
    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this, ContainerActivity.class));
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }
}
