package com.oblivion.tokoonline.view.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.view.ChoseCategorySell;
import com.oblivion.tokoonline.view.ItemViewByCategory;
import com.oblivion.tokoonline.view.NextSellActivity;

public class ElectronicActivity extends AppCompatActivity {


    private String urlLocationPhoto;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic);

        urlLocationPhoto = getIntent().getStringExtra("urlLocationPhoto");
        activity = getIntent().getStringExtra("activity");
    }

    public void handphone_click(View view) {

       updateUI("Handphone");

    }

    public void computer_click(View view) {

        updateUI("Komputer");

    }

    public void tablet_click(View view) {

        updateUI("Tablet");

    }

    public void television_click(View view) {

        updateUI("Televisi");

    }


    public void camera_click(View view) {

        updateUI("Kamera");

    }

    public void laptop_click(View view) {

        updateUI("Laptop");

    }


    private void updateUI(String category){
        if (activity != null){

            Intent intent = new Intent(ElectronicActivity.this, NextSellActivity.class);
            intent.putExtra("subKategori", category);
            intent.putExtra("urlLocationPhoto", urlLocationPhoto);
            startActivity(intent);

        }else {

            Intent intent = new Intent(ElectronicActivity.this, ItemViewByCategory.class);
            intent.putExtra("subKategori", category);
            startActivity(intent);
        }

    }
}
