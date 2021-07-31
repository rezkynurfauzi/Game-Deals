package com.apps.rezky.gamedeals.view.search;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    30 juli 2021
*/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
import com.apps.rezky.gamedeals.Adapter.SearchRecycleView;
import com.apps.rezky.gamedeals.api.APIData;
import com.apps.rezky.gamedeals.view.article.ArticleActivity;
import com.apps.rezky.gamedeals.view.freebie.FreebieActivity;
import com.apps.rezky.gamedeals.view.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchRecycleView.OnItemClickListener {

    //inisialisasi variable
    private ImageButton btnback;
    RecyclerView searchrecyclerView;
    List<APIData> searches; //pembuatan list yang mereference data dari java class APIData untuk menampung data hasil dari activity_search
    SearchRecycleView searchRecycleView; //inisialisasi variable untuk adaptor SearchRecycleView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnback = findViewById(R.id.search_back_button);

        //mengambil query text dari search bar pada HomeActivity
        Intent searchintent = getIntent();
        String game = searchintent.getStringExtra(HomeActivity.EXTRA_TEXT);

        //menyambugkan textview untuk menampilkan query yang dimasukan user
        TextView gametext = findViewById(R.id.search_game_title);
        gametext.setText(game); //set text query inputan user di textview

        searchrecyclerView = findViewById(R.id.searchList); //menyambungka recycleview yang ada pada activity_search
        searches = new ArrayList<>(); //pembuatan ArrayList yang akan menampung data dari API
        ParseJSON(); //pemanggilan fungsi untuk parsing JSON

        //button back listener
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

        //inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //menu search terpilih
        bottomNavigationView.setSelectedItemId(R.id.home);

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
                        startActivity(new Intent(getApplicationContext()
                                , FreebieActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void ParseJSON() {
        //fungsi untuk memparsing JSON untuk mencari data game sesuai inputan user
        Intent searchintent = getIntent();
        String gamesearch = searchintent.getStringExtra(HomeActivity.EXTRA_TEXT);
        RequestQueue queue = Volley.newRequestQueue(this);
        gamesearch = gamesearch.replaceAll(" ","%20"); //menggati karakter spasi menjadi %20 untuk merequest ke API
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://www.cheapshark.com/api/1.0/deals?storeID=1&title="+gamesearch, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject gameObject = response.getJSONObject(i);

                        //pengambilan data dari API sesuai kebutuhan
                        APIData search = new APIData();
                        search.setTitle(gameObject.getString("title"));
                        search.setDealPrice(gameObject.getString("salePrice"));
                        search.setNormalPrice(gameObject.getString("normalPrice"));
                        search.setCoverImage(gameObject.getString("steamAppID"));
                        search.setDiscount(gameObject.getString("savings"));
                        search.setMetacritic(gameObject.getString("steamRatingPercent"));
                        search.setSteamreview(gameObject.getString("steamRatingText"));
                        //menambahkan data yang sudah terambil kedalam Array List
                        searches.add(search);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                searchrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                searchRecycleView = new SearchRecycleView(getApplicationContext(),searches);
                searchrecyclerView.setAdapter(searchRecycleView); //menyambungkan adapater
                searchRecycleView.setOnItemClickListener(SearchActivity.this); //pemanggilan fungsi jika item pada recycleview ditekan
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
        //fungsi untuk item pada recycleview jika ditekan
        APIData clickedItem = searches.get(position);
        Intent intentstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.steampowered.com/app/"+clickedItem.getCoverImage()));
        startActivity(intentstore);
    }

}
