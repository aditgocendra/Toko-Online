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

public class ElectronicActivity extends AppCompatActivity {


    private String urlLocationPhoto;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic);

        urlLocationPhoto = getIntent().getStringExtra("urlLocationPhoto");
        activity = getIntent().getStringExtra("activity");

        Toolbar toolbar = findViewById(R.id.toolbar_electronic);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity != null){

                    Intent intent = new Intent(ElectronicActivity.this, ChoseCategorySell.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ElectronicActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });
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
            intent.putExtra("activity", "electronic");
            intent.putExtra("subKategori", category);
            startActivity(intent);
        }

    }
}
