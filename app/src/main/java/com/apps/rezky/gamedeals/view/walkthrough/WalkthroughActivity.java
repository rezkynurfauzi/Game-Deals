package com.apps.rezky.gamedeals.view.walkthrough;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    30 juli 2021
*/

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.apps.rezky.gamedeals.R;
import com.apps.rezky.gamedeals.Adapter.WalkthroughViewPager;
import com.apps.rezky.gamedeals.view.home.HomeActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WalkthroughActivity extends AppCompatActivity {

    private ViewPager screenPager;
    WalkthroughViewPager walkthroughViewPager;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    TextView btnSkip;
    Animation btnAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //menge-check jika user sudah melewati walkthrough
        if (restorePrefData()) {
            Intent homeActivity = new Intent(getApplicationContext(),HomeActivity.class );
            startActivity(homeActivity);
            finish();
        }

        setContentView(R.layout.activity_walkthrough);

        //deklarasi variable
        tabIndicator = findViewById(R.id.tab_indicator);
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.txt_skip);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_animation);


        //menambhakan data untuk ditampilkan di recycle view
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("AHH THATS HOT","See the hottest deals available righ now",R.drawable.asset_walkthrough_1));
        mList.add(new ScreenItem("SEARCH FOR THE PRICE","Search for the price for your desired games",R.drawable.asset_walkthrough_2));
        mList.add(new ScreenItem("ITS POPPY GLORIA","Read the latest gaming & movies news & article",R.drawable.asset_walkthrough_3));
        mList.add(new ScreenItem("ITS FREE REAL ESTATE","See what freebie offer you can get",R.drawable.asset_walkthrough_4));

        //setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        walkthroughViewPager = new WalkthroughViewPager(this,mList);
        screenPager.setAdapter(walkthroughViewPager);

        //setup indicator
        tabIndicator.setupWithViewPager(screenPager);

        //text skip listener
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        //button next listener on click
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size() && btnNext.getText() != "Lets Go!"){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                else if (btnNext.getText()=="Lets Go!"){
                    Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(homeIntent);
                    savePrefsData();
                    finish();
                }
                if (position == mList.size()-1 && btnNext.getText()!="Lets Go!"){
                    loadLastScreen();
                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==mList.size()-1 && btnNext.getText()!="Lets Go!"){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean WalkthroughStatus = pref.getBoolean("WalkthroughOpened",false);
        return  WalkthroughStatus;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("WalkthroughOpened",true);
        editor.commit();
    }

    private void loadLastScreen() {
        btnNext.setAnimation(btnAnim);
        btnNext.setText("Lets Go!");
        tabIndicator.setVisibility(View.INVISIBLE);
        btnSkip.setVisibility(View.INVISIBLE);
    }

}
