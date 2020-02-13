package com.oblivion.tokoonline.view.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.view.ItemViewByCategory;
import com.oblivion.tokoonline.view.NextSellActivity;

public class VehicleActivity extends AppCompatActivity {

    private String urlLocationPhoto;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        urlLocationPhoto = getIntent().getStringExtra("urlLocationPhoto");
        activity = getIntent().getStringExtra("activity");
    }

    public void car_click(View view) {
        updateUI("Motor");
    }

    public void motorcycle_click(View view) {
        updateUI("Mobil");
    }

    private void updateUI(String category){
        if (activity != null){

            Intent intent = new Intent(VehicleActivity.this, NextSellActivity.class);
            intent.putExtra("subKategori", category);
            intent.putExtra("urlLocationPhoto", urlLocationPhoto);
            startActivity(intent);

        }else {

            Intent intent = new Intent(VehicleActivity.this, ItemViewByCategory.class);
            intent.putExtra("subKategori", category);
            startActivity(intent);
        }

    }
}
