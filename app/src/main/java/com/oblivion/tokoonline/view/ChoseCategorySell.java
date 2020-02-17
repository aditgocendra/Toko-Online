package com.oblivion.tokoonline.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.view.category.AccElectronicActivity;
import com.oblivion.tokoonline.view.category.BookAndPenActivity;
import com.oblivion.tokoonline.view.category.ElectronicActivity;
import com.oblivion.tokoonline.view.category.FashionActivity;
import com.oblivion.tokoonline.view.category.FoodAndDrinkActivity;
import com.oblivion.tokoonline.view.category.VehicleActivity;

public class ChoseCategorySell extends AppCompatActivity {

    String urlLocationPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_category_sell);

        urlLocationPhoto = getIntent().getStringExtra("urlLocationPhoto");


        Toolbar toolbar = findViewById(R.id.toolbar_chose);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(ChoseCategorySell.this, MainActivity.class);
                    intent.putExtra("activity", "sellActivity");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
            }
        });

    }

    public void pick_kat_electronic(View view) {

            Intent intent = new Intent(ChoseCategorySell.this, ElectronicActivity.class);
            intent.putExtra("activity", "ChoseCategory");
            intent.putExtra("urlLocationPhoto", urlLocationPhoto);
            startActivity(intent);


    }

    public void pick_kat_acc_electronic(View view) {
        Intent intent = new Intent(ChoseCategorySell.this, AccElectronicActivity.class);
        intent.putExtra("activity", "ChoseCategory");
        intent.putExtra("urlLocationPhoto", urlLocationPhoto);
        startActivity(intent);
    }

    public void pick_kat_book_and_pen(View view) {
        Intent intent = new Intent(ChoseCategorySell.this, BookAndPenActivity.class);
        intent.putExtra("activity", "ChoseCategory");
        intent.putExtra("urlLocationPhoto", urlLocationPhoto);
        startActivity(intent);

    }

    public void pick_kat_vehicle(View view) {
        Intent intent = new Intent(ChoseCategorySell.this, VehicleActivity.class);
        intent.putExtra("activity", "ChoseCategory");
        intent.putExtra("urlLocationPhoto", urlLocationPhoto);
        startActivity(intent);
    }

    public void pick_kat_food_and_drink(View view) {
        Intent intent = new Intent(ChoseCategorySell.this, FoodAndDrinkActivity.class);
        intent.putExtra("activity", "ChoseCategory");
        intent.putExtra("urlLocationPhoto", urlLocationPhoto);
        startActivity(intent);
    }

    public void pick_kat_fashion(View view) {
        Intent intent = new Intent(ChoseCategorySell.this, FashionActivity.class);
        intent.putExtra("activity", "ChoseCategory");
        intent.putExtra("urlLocationPhoto", urlLocationPhoto);
        startActivity(intent);
    }
}
