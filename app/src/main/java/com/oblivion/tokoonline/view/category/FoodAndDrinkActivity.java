package com.oblivion.tokoonline.view.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.view.ChoseCategorySell;
import com.oblivion.tokoonline.view.ItemViewByCategory;
import com.oblivion.tokoonline.view.MainActivity;
import com.oblivion.tokoonline.view.NextSellActivity;

import java.util.Objects;

public class FoodAndDrinkActivity extends AppCompatActivity {

    private String urlLocationPhoto;
    private String activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_and_drink);

        urlLocationPhoto = getIntent().getStringExtra("urlLocationPhoto");
        activity = getIntent().getStringExtra("activity");

        Toolbar toolbar = findViewById(R.id.toolbar_fooddrink);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity != null){

                    Intent intent = new Intent(FoodAndDrinkActivity.this, ChoseCategorySell.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(FoodAndDrinkActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });
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
            intent.putExtra("activity", "foodanddrink");
            startActivity(intent);
        }

    }
}
