package com.oblivion.tokoonline.view.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.view.ItemViewByCategory;
import com.oblivion.tokoonline.view.NextSellActivity;

public class AccElectronicActivity extends AppCompatActivity {

    private String urlLocationPhoto;
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_electronic);

        urlLocationPhoto = getIntent().getStringExtra("urlLocationPhoto");
        activity = getIntent().getStringExtra("activity");
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
            intent.putExtra("subKategori", category);
            startActivity(intent);
        }

    }
}
