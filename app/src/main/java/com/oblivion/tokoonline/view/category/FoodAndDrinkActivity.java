package com.oblivion.tokoonline.view.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.view.ItemViewByCategory;
import com.oblivion.tokoonline.view.NextSellActivity;

public class FoodAndDrinkActivity extends AppCompatActivity {

    private String urlLocationPhoto;
    private String activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_and_drink);

        urlLocationPhoto = getIntent().getStringExtra("urlLocationPhoto");
        activity = getIntent().getStringExtra("activity");
    }

    public void food_click(View view) {
        updateUI("Makanan");
    }

    public void drink_click(View view) {
        updateUI("Minuman");
    }

    private void updateUI(String category){
        if (activity != null){

            Intent intent = new Intent(FoodAndDrinkActivity.this, NextSellActivity.class);
            intent.putExtra("subKategori", category);
            intent.putExtra("urlLocationPhoto", urlLocationPhoto);
            startActivity(intent);

        }else {

            Intent intent = new Intent(FoodAndDrinkActivity.this, ItemViewByCategory.class);
            intent.putExtra("subKategori", category);
            startActivity(intent);
        }

    }
}
