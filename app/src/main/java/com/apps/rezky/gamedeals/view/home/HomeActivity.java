package com.apps.rezky.gamedeals.view.home;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    29 juli 2021
*/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.apps.rezky.gamedeals.R;
import com.apps.rezky.gamedeals.Adapter.DealRecycleView;
import com.apps.rezky.gamedeals.Adapter.HotDealRecycleView;
import com.apps.rezky.gamedeals.api.APIData;
import com.apps.rezky.gamedeals.view.article.ArticleActivity;
import com.apps.rezky.gamedeals.view.freebie.FreebieActivity;
import com.apps.rezky.gamedeals.view.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements DealRecycleView.OnItemClickListener,HotDealRecycleView.HotOnItemClickListener{

    //inisialisasi variable
    RecyclerView recyclerView;
    RecyclerView hotrecyclerView;
    //pembuatan list dengan model APIData
    List<APIData> deals;
    List<APIData> hotdeals;
    private static String JSON_URL = "https://www.cheapshark.com/api/1.0/deals?storeID=1&sortBy=Metacritic&onSale=1"; //halaman request API top games on sale
    private static String JSON_URL_HOT = "https://www.cheapshark.com/api/1.0/deals?storeID=1&onSale=1";//halaman request API untuk hotest deals
    DealRecycleView dealRecycleView; //pembuatan variable untuk adapter DealRecycleView
    HotDealRecycleView hotdealRecycleView; //pembuatan variable untuk adapater HotDealRecycleView

    public static final String EXTRA_TEXT = "com.elraiz.mygamedeal.home.EXTRA_TEXT"; //string untuk mengoper teks dari search bar ke activity_search

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText searchGames = findViewById(R.id.home_search_game); //menyambubgkan variable ke EditText pada activity_home

        searchGames.setOnEditorActionListener(editorActionListener); //mengecheck jika ada action pada searchbar

        recyclerView = findViewById(R.id.dealList); //membuat variable untuk recycleview menampilkan top games on sale
        hotrecyclerView = findViewById(R.id.hotdealList); //membuat variable untuk recycleview menampilkan hottest deals
        //Pembuatan variable Array List untuk menampung data dari API
        hotdeals = new ArrayList<>();
        deals = new ArrayList<>();
        //pemanggilan fungsi untuk parsing JSON
        ParseJSON();
        ParseJSONHot();

        //inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //menu home terpilih
        bottomNavigationView.setSelectedItemId(R.id.home);

        //navigasi pindah aktivitas
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.article:
                        startActivity(new Intent(getApplicationContext()
                            , ArticleActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.freebie:
                        startActivity(new Intent(getApplicationContext()
                                , FreebieActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            EditText searchGame = findViewById(R.id.home_search_game);
            String game = searchGame.getText().toString(); //mengambil teks inputan user pada search bar

            //mengirim varible teks ke activity_search
            Intent searchintent = new Intent(HomeActivity.this, SearchActivity.class);
            searchintent.putExtra(EXTRA_TEXT,game);
            startActivity(searchintent);
            return false;
        }
    };

    private void ParseJSON() {
        RequestQueue queue = Volley.newRequestQueue(this);
        //merequest ke API
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject gameObject = response.getJSONObject(i);

                        //pengambilan data dari API sesuai kebutuhan
                        APIData deal = new APIData();
                        deal.setTitle(gameObject.getString("title"));
                        deal.setDealPrice(gameObject.getString("salePrice"));
                        deal.setNormalPrice(gameObject.getString("normalPrice"));
                        deal.setCoverImage(gameObject.getString("steamAppID"));
                        deal.setDiscount(gameObject.getString("savings"));
                        deal.setMetacritic(gameObject.getString("metacriticScore"));
                        deal.setSteamreview(gameObject.getString("steamRatingText"));
                        //menambahkan data yang sudah terambil kedalam Array List
                        deals.add(deal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                dealRecycleView = new DealRecycleView(getApplicationContext(),deals);
                recyclerView.setAdapter(dealRecycleView); //menyambungkan data ke adapter
                dealRecycleView.setOnItemClickListener(HomeActivity.this); //pemanggilan fungsi jika item pada recycleview ini ditekan
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }
    private void ParseJSONHot() {
        RequestQueue queue = Volley.newRequestQueue(this);
        //request ke API
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL_HOT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < 15; i++) {
                    try {
                        JSONObject songObject = response.getJSONObject(i);

                        //pengambilan data dari API
                        APIData hotdeal = new APIData();
                        hotdeal.setDealPrice(songObject.getString("salePrice"));
                        hotdeal.setNormalPrice(songObject.getString("normalPrice"));
                        hotdeal.setCoverImage(songObject.getString("steamAppID"));
                        hotdeal.setDiscount(songObject.getString("savings"));
                        //memasukan dari yang sudah terambil kedalam Array List
                        hotdeals.add(hotdeal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL); //set untuk tampilan recycleview menjadi horizontal
                hotrecyclerView.setLayoutManager(manager);
                hotdealRecycleView = new HotDealRecycleView(getApplicationContext(),hotdeals);
                hotrecyclerView.setAdapter(hotdealRecycleView); //menyambungkan data ke adapter
                hotdealRecycleView.setHotOnItemClickListener(HomeActivity.this); //pemanggilan fungsin jika item pada recycleview ditekan
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(int position) {
        //fungsi untuk merespons jika item pada recycleview ditekan
        APIData clickedItem = deals.get(position);
        Intent intentstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.steampowered.com/app/"+clickedItem.getCoverImage()));
        startActivity(intentstore);
    }


    @Override
    public void hotonItemClick(int position) {
        //fungsi untuk merespons jika item pada recycleview ditekan
        APIData clickedItem = hotdeals.get(position);
        Intent intentstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.steampowered.com/app/"+clickedItem.getCoverImage()));
        startActivity(intentstore);
    }
}
