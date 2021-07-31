package com.apps.rezky.gamedeals.view.freebie;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    29 juli 2021
*/

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.rezky.gamedeals.R;
import com.apps.rezky.gamedeals.view.article.ArticleActivity;
import com.apps.rezky.gamedeals.view.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FreebieActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freebie);

        //inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        webView = findViewById(R.id.freebie_web);
        WebSettings webSettings = webView.getSettings();

        webSettings.getLoadsImagesAutomatically();
        webSettings.setJavaScriptEnabled(true);

        //load tampilan web freebie
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.indiegamebundles.com/category/free/");

        //menu freebie terpilih
        bottomNavigationView.setSelectedItemId(R.id.freebie);

        //navigasi pindah aktivitas
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.article:
                        startActivity(new Intent(getApplicationContext()
                                , ArticleActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.freebie:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        else {
        super.onBackPressed();
        }
    }
}
