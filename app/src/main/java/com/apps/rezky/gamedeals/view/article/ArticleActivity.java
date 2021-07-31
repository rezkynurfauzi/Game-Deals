package com.apps.rezky.gamedeals.view.article;

/*
    Created by Rezky Nur Fauzi - 10118016 - IF1
    29 juli 2021
*/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apps.rezky.gamedeals.R;
import com.apps.rezky.gamedeals.Adapter.ArticleRecycleView;
import com.apps.rezky.gamedeals.api.APIDataArticle;
import com.apps.rezky.gamedeals.view.freebie.FreebieActivity;
import com.apps.rezky.gamedeals.view.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity implements ArticleRecycleView.OnItemClickListener {

    //deklarasi variable
    private RecyclerView recyclerView;
    private ArticleRecycleView articlerecycleView; //variable untuk memanggil java class ArticleRecycleView
    private ArrayList<APIDataArticle> articles; //pembuatan arraylist yang akan menampung data dari API
    private static String JSON_URL = "https://www.gamespot.com/api/articles/?api_key=ed7d5cd0b92abd729fc9af24e022d79d9dcd4d91&sort=publish_date:desc&format=json&limit=20"; //halaman request data dari API
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        recyclerView = findViewById(R.id.articleList); //menyambungkan recycleview yang ada pada activity_article
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articles = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        //inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //menu search terpilih
        bottomNavigationView.setSelectedItemId(R.id.article);

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

    private void parseJSON() {
        //fungsi parsing JSON untuk data request yang diterima dari API
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                //pengambila dari dari API sesuai kebutuhan
                                String articleTitle = hit.getString("title");
                                String articleSummary = hit.getString("deck");
                                String articleAuthor = hit.getString("authors");
                                JSONObject images = hit.getJSONObject("image");
                                String imageURL = images.getString("original");
                                String articlePublishDate = hit.getString("publish_date");
                                String articlewebURL = hit.getString("site_detail_url");
                                //menambahkan data yang sudah diambil kedalam Array List yang sudah dibuat
                                articles.add(new APIDataArticle(articleTitle,articleSummary,articleAuthor,imageURL,articlePublishDate,articlewebURL));
                            }
                            articlerecycleView = new ArticleRecycleView(ArticleActivity.this, articles);
                            recyclerView.setAdapter(articlerecycleView); //menyambungkan data ke recycleview
                            articlerecycleView.setOnItemClickListener(ArticleActivity.this); //pemanggilan fungsi item on click
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(int position) {
        //fungsi untuk merespons jika item pada recycleview ditekan
        APIDataArticle clickedItem = articles.get(position);
        Intent intentarticle = new Intent(Intent.ACTION_VIEW, Uri.parse(clickedItem.getWebUrl()));
        startActivity(intentarticle);
    }
}
