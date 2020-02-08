package com.oblivion.tokoonline.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.fragment.ProvinceFragment;

public class PickLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);


        final Fragment fragment = new ProvinceFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Pilih Provinsi");
        fragmentTransaction.commit();

        ImageView back_topbar = findViewById(R.id.tool_back);

        back_topbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(PickLocationActivity.this, MainActivity.class);
                    startActivity(intent);

            }
        });

    }
}
