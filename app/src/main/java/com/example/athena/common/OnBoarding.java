package com.example.athena.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.athena.HelperClasses.SliderAdapter;
import com.example.athena.R;
import com.example.athena.user.UserDashboard;

public class OnBoarding extends AppCompatActivity {

    ViewPager2 viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button letsGetStarted;
    Animation animation;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAGS_CHANGED,WindowManager.LayoutParams.FLAGS_CHANGED);
        setContentView(R.layout.activity_on_boarding);

        //Hooks
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.get_started);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.registerOnPageChangeCallback(changeCallback);


    }
    public void skip(View view){

        startActivity(new Intent(this, OnBoarding.class));
        finish();
    }
    public void next(View view){
        viewPager.setCurrentItem(currentPos + 1);

    }

    public void get_started(View view){
        startActivity(new Intent(this, Welcome.class));
        finish();
    }
    private void addDots(int position){
        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for(int i=0;i< dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.purple_700));
        }
    }
    ViewPager2.OnPageChangeCallback changeCallback= new ViewPager2.OnPageChangeCallback(){
        @Override
        public void onPageSelected(int position) {
            // Handle page selection here
            addDots(position);
            currentPos=position;
            if(position==0){
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else if (position==1) {
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else{
                animation= AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_anim);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

}