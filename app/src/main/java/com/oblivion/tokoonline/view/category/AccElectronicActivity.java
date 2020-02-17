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

public class AccElectronicActivity extends AppCompatActivity {

    private String urlLocationPhoto;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_electronic);

        urlLocationPhoto = getIntent().getStringExtra("urlLocationPhoto");
        activity = getIntent().getStringExtra("activity");


        Toolbar toolbar = findViewById(R.id.toolbar_acc);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activity != null){

                    Intent intent = new Intent(AccElectronicActivity.this, ChoseCategorySell.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(AccElectronicActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });
    }

    public void headset_click(View view) {
        updateUI("Headset");

    }

    public void keyboard_click(View view) {
        updateUI("Keyboard");

    }

    public void sim_card(View view) {
        updateUI("Kartu Sim");

    }

    public void mouse_click(View view) {
        updateUI("Mouse");

    }

    public void speaker_click(View view) {
        updateUI("Speaker");

    }

    public void monitor_click(View view) {
        updateUI("Monitor");

    }

    private void updateUI(String category){
        if (activity != null){

            Intent intent = new Intent(AccElectronicActivity.this, NextSellActivity.class);
            intent.putExtra("subKategori", category);
            intent.putExtra("urlLocationPhoto", urlLocationPhoto);
            startActivity(intent);

        }else {

            Intent intent = new Intent(AccElectronicActivity.this, ItemViewByCategory.class);
            intent.putExtra("activity", "AccElectronic");
            intent.putExtra("subKategori", category);
            startActivity(intent);
        }

    }
}
